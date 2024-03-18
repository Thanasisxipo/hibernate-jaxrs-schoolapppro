package gr.aueb.cf.schoolapppro.rest;

import gr.aueb.cf.schoolapppro.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapppro.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapppro.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolapppro.mapper.Mapper;
import gr.aueb.cf.schoolapppro.model.Teacher;
import gr.aueb.cf.schoolapppro.service.ITeacherService;
import gr.aueb.cf.schoolapppro.service.exception.EntityNotFoundException;
import gr.aueb.cf.schoolapppro.validator.ValidatorUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Path("/teachers")
public class TeacherRestController {
    @Inject
    private ITeacherService teacherService;

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTeacher(TeacherInsertDTO dto, @Context UriInfo uriInfo) {
        List<String> errors = ValidatorUtil.validateDTO(dto);

        if (!errors.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }

        try {
            Teacher teacher = teacherService.insertTeacher(dto);
            TeacherReadOnlyDTO readOnlyDTO = Mapper.mapTeacherToReadOnly(teacher);
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
    public Response updateTeacher(@PathParam("id") Long id, TeacherUpdateDTO dto) {
        if (!Objects.equals(dto.getId(), id)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized").build();
        }
        List<String> errors = ValidatorUtil.validateDTO(dto);
        if (!errors.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }
        try {
            Teacher teacher = teacherService.updateTeacher(dto);
            TeacherReadOnlyDTO readOnlyDTO = Mapper.mapTeacherToReadOnly(teacher);
            return Response.status(Response.Status.OK).entity(readOnlyDTO).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

    }

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeachersByLastname(@QueryParam("lastname") String lastname) {
        List<Teacher> teachers;
        try {
            teachers = teacherService.getTeacherByLastname(lastname);
            List<TeacherReadOnlyDTO> readOnlyDTOS = new ArrayList<>();
            for (Teacher teacher : teachers) {
                readOnlyDTOS.add(Mapper.mapTeacherToReadOnly(teacher));
            }
            return Response.status(Response.Status.OK).entity(readOnlyDTOS).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneTeacher(@PathParam("id") Long id) {
        Teacher teacher;
        try {
            teacher = teacherService.getTeacherById(id);
            TeacherReadOnlyDTO readOnlyDTO = Mapper.mapTeacherToReadOnly(teacher);
            return Response.status(Response.Status.OK).entity(readOnlyDTO).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTeacher(@PathParam("id") Long id) {
        Teacher teacher;
        try {
            teacher = teacherService.getTeacherById(id);
            teacherService.deleteTeacher(id);
            TeacherReadOnlyDTO readOnlyDTO = Mapper.mapTeacherToReadOnly(teacher);
            return Response.status(Response.Status.OK).entity(readOnlyDTO).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
