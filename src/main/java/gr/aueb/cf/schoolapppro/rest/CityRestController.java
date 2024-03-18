package gr.aueb.cf.schoolapppro.rest;

import gr.aueb.cf.schoolapppro.dto.*;
import gr.aueb.cf.schoolapppro.mapper.Mapper;
import gr.aueb.cf.schoolapppro.model.City;
import gr.aueb.cf.schoolapppro.service.ICityService;
import gr.aueb.cf.schoolapppro.service.exception.EntityNotFoundException;
import gr.aueb.cf.schoolapppro.validator.ValidatorUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Path("/cities")
public class CityRestController {

    @Inject
    private ICityService service;


    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCity(CityInsertDTO dto, @Context UriInfo uriInfo) {
        List<String> errors = ValidatorUtil.validateDTO(dto);

        if (!errors.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }

        try {
            City city = service.insertCity(dto);
            CityReadOnlyDTO readOnlyDTO = Mapper.mapCityToReadOnly(city);
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
    public Response updateCity(@PathParam("id") Long id, CityUpdateDTO dto) {
        if (!Objects.equals(dto.getId(), id)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized").build();
        }
        List<String> errors = ValidatorUtil.validateDTO(dto);
        if (!errors.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }
        try {
            City city = service.updateCity(dto);
            CityReadOnlyDTO readOnlyDTO = Mapper.mapCityToReadOnly(city);
            return Response.status(Response.Status.OK).entity(readOnlyDTO).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

    }

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCitiesByCityname(@QueryParam("city") String city) {
        List<City> cities;
        try {
            cities = service.getCityByCity(city);
            List<CityReadOnlyDTO> readOnlyDTOS = new ArrayList<>();
            for (City city1 : cities) {
                readOnlyDTOS.add(Mapper.mapCityToReadOnly(city1));
            }
            return Response.status(Response.Status.OK).entity(readOnlyDTOS).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneCity(@PathParam("id") Long id) {
        City city;
        try {
            city = service.getCityById(id);
            CityReadOnlyDTO readOnlyDTO = Mapper.mapCityToReadOnly(city);
            return Response.status(Response.Status.OK).entity(readOnlyDTO).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCity(@PathParam("id") Long id) {
        City city;
        try {
            city = service.getCityById(id);
            service.deleteCity(id);
            CityReadOnlyDTO readOnlyDTO = Mapper.mapCityToReadOnly(city);
            return Response.status(Response.Status.OK).entity(readOnlyDTO).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
