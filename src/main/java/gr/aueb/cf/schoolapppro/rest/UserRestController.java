package gr.aueb.cf.schoolapppro.rest;

import gr.aueb.cf.schoolapppro.dto.*;
import gr.aueb.cf.schoolapppro.mapper.Mapper;
import gr.aueb.cf.schoolapppro.model.Teacher;
import gr.aueb.cf.schoolapppro.model.User;
import gr.aueb.cf.schoolapppro.service.IUserService;
import gr.aueb.cf.schoolapppro.service.exception.EntityNotFoundException;
import gr.aueb.cf.schoolapppro.validator.ValidatorUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Path("/users")
public class UserRestController {

    @Inject
    private IUserService service;

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(UserInsertDTO dto, @Context UriInfo uriInfo) {
        List<String> errors = ValidatorUtil.validateDTO(dto);

        if (!errors.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }

        try {
            User user = service.insertUser(dto);
            UserReadOnlyDTO readOnlyDTO = Mapper.mapUserToReadOnly(user);
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            return Response.status(Response.Status.CREATED)
                    .entity(readOnlyDTO)
                    .location(uriBuilder.path(Long.toString(readOnlyDTO.getId())).build()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") Long id, UserUpdateDTO dto) {
        if (!Objects.equals(dto.getId(), id)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized").build();
        }
        List<String> errors = ValidatorUtil.validateDTO(dto);
        if (!errors.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }
        try {
            User user = service.updateUser(dto);
            UserReadOnlyDTO readOnlyDTO = Mapper.mapUserToReadOnly(user);
            return Response.status(Response.Status.OK).entity(readOnlyDTO).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

    }

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersByUsername(@QueryParam("username") String username) {
        List<User> users;
        try {
            users = service.getUserByUsername(username);
            List<UserReadOnlyDTO> readOnlyDTOS = new ArrayList<>();
            for (User user : users) {
                readOnlyDTOS.add(Mapper.mapUserToReadOnly(user));
            }
            return Response.status(Response.Status.OK).entity(readOnlyDTOS).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneUser(@PathParam("id") Long id) {
        User user;
        try {
            user = service.getUserById(id);
            UserReadOnlyDTO readOnlyDTO = Mapper.mapUserToReadOnly(user);
            return Response.status(Response.Status.OK).entity(readOnlyDTO).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") Long id) {
        User user;
        try {
            user = service.getUserById(id);
            service.deleteUser(id);
            UserReadOnlyDTO readOnlyDTO = Mapper.mapUserToReadOnly(user);
            return Response.status(Response.Status.OK).entity(readOnlyDTO).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
