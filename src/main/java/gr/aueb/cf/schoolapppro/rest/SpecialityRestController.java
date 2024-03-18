package gr.aueb.cf.schoolapppro.rest;

import gr.aueb.cf.schoolapppro.dto.*;
import gr.aueb.cf.schoolapppro.mapper.Mapper;
import gr.aueb.cf.schoolapppro.model.Speciality;
import gr.aueb.cf.schoolapppro.model.Teacher;
import gr.aueb.cf.schoolapppro.service.ISpecialityService;
import gr.aueb.cf.schoolapppro.service.exception.EntityNotFoundException;
import gr.aueb.cf.schoolapppro.validator.ValidatorUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Path("/specialities")
public class SpecialityRestController {

    @Inject
    private ISpecialityService service;

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSpeciality(SpecialityInsertDTO dto, @Context UriInfo uriInfo) {
        List<String> errors = ValidatorUtil.validateDTO(dto);

        if (!errors.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }

        try {
            Speciality speciality = service.insertSpeciality(dto);
            SpecialityReadOnlyDTO readOnlyDTO = Mapper.mapSpecialityToReadOnly(speciality);
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
    public Response updateSpeciality(@PathParam("id") Long id, SpecialityUpdateDTO dto) {
        if (!Objects.equals(dto.getId(), id)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized").build();
        }
        List<String> errors = ValidatorUtil.validateDTO(dto);
        if (!errors.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }
        try {
            Speciality speciality = service.updateSpeciality(dto);
            SpecialityReadOnlyDTO readOnlyDTO = Mapper.mapSpecialityToReadOnly(speciality);
            return Response.status(Response.Status.OK).entity(readOnlyDTO).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

    }

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSpecialitiesBySpecialityname(@QueryParam("speciality") String specialityname) {
        List<Speciality> specialities;
        try {
            specialities = service.getSpecialityBySpeciality(specialityname);
            List<SpecialityReadOnlyDTO> readOnlyDTOS = new ArrayList<>();
            for (Speciality speciality : specialities) {
                readOnlyDTOS.add(Mapper.mapSpecialityToReadOnly(speciality));
            }
            return Response.status(Response.Status.OK).entity(readOnlyDTOS).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneSpeciality(@PathParam("id") Long id) {
        Speciality speciality;
        try {
            speciality = service.getSpecialityById(id);
            SpecialityReadOnlyDTO readOnlyDTO = Mapper.mapSpecialityToReadOnly(speciality);
            return Response.status(Response.Status.OK).entity(readOnlyDTO).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSpeciality(@PathParam("id") Long id) {
        Speciality speciality;
        try {
            speciality = service.getSpecialityById(id);
            service.deleteSpeciality(id);
            SpecialityReadOnlyDTO readOnlyDTO = Mapper.mapSpecialityToReadOnly(speciality);
            return Response.status(Response.Status.OK).entity(readOnlyDTO).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
