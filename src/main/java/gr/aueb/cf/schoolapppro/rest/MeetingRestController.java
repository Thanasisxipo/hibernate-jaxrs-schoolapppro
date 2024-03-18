package gr.aueb.cf.schoolapppro.rest;

import gr.aueb.cf.schoolapppro.dto.*;
import gr.aueb.cf.schoolapppro.mapper.Mapper;
import gr.aueb.cf.schoolapppro.model.Meeting;
import gr.aueb.cf.schoolapppro.model.Teacher;
import gr.aueb.cf.schoolapppro.service.IMeetingService;
import gr.aueb.cf.schoolapppro.service.exception.EntityNotFoundException;
import gr.aueb.cf.schoolapppro.validator.ValidatorUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Path("/meetings")
public class MeetingRestController {

    @Inject
    private IMeetingService service;

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMeeting(MeetingInsertDTO dto, @Context UriInfo uriInfo) {
        List<String> errors = ValidatorUtil.validateDTO(dto);

        if (!errors.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }

        try {
            Meeting meeting = service.insertMeeting(dto);
            MeetingReadOnlyDTO readOnlyDTO = Mapper.mapMeetingToReadOnly(meeting);
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
    public Response updateMeeting(@PathParam("id") Long id, MeetingUpdateDTO dto) {
        if (!Objects.equals(dto.getId(), id)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized").build();
        }
        List<String> errors = ValidatorUtil.validateDTO(dto);
        if (!errors.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }
        try {
            Meeting meeting = service.updateMeeting(dto);
            MeetingReadOnlyDTO readOnlyDTO = Mapper.mapMeetingToReadOnly(meeting);
            return Response.status(Response.Status.OK).entity(readOnlyDTO).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

    }

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMeetingsByRoom(@QueryParam("meetingRoom") String room) {
        List<Meeting> meetings;
        try {
            meetings = service.getMeetingByMeetingRoom(room);
            List<MeetingReadOnlyDTO> readOnlyDTOS = new ArrayList<>();
            for (Meeting meeting : meetings) {
                readOnlyDTOS.add(Mapper.mapMeetingToReadOnly(meeting));
            }
            return Response.status(Response.Status.OK).entity(readOnlyDTOS).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneMeeting(@PathParam("id") Long id) {
        Meeting meeting;
        try {
            meeting = service.getMeetingById(id);
            MeetingReadOnlyDTO readOnlyDTO = Mapper.mapMeetingToReadOnly(meeting);
            return Response.status(Response.Status.OK).entity(readOnlyDTO).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMeeting(@PathParam("id") Long id) {
        Meeting meeting;
        try {
            meeting = service.getMeetingById(id);
            service.deleteMeeting(id);
            MeetingReadOnlyDTO readOnlyDTO = Mapper.mapMeetingToReadOnly(meeting);
            return Response.status(Response.Status.OK).entity(readOnlyDTO).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
