/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import beansDB.UzytkownikBean;
import entitys.Uzytkownik;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Marcin
 */
@Stateless
@Path("uzytkownik")
public class UzytkownikFacadeREST extends AbstractFacade<Uzytkownik> {

    @PersistenceContext(unitName = "WebApplication1PU")
    private EntityManager em;

    @EJB
    private UzytkownikBean uzytkownikBean;

    public UzytkownikFacadeREST() {
        super(Uzytkownik.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(Uzytkownik entity) {
        super.create(entity);
    }

    @POST
    @Path("/dodaj")
    @Consumes({"application/json"})
    public Response dodaj(Uzytkownik entity) {
        entity.setDataZalozenia(new Date());
        if (uzytkownikBean.checkSingleUser(entity.getLogin())) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        uzytkownikBean.add(entity);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    public Response edit(@PathParam("id") Long id, Uzytkownik entity) {
        entity.setId(id);
        if (uzytkownikBean.checkSingleUser(entity.getLogin())) {
            uzytkownikBean.zamien(entity);
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Uzytkownik find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Path("/get/{login}/{pass}")
    @Produces({"application/json"})
    public Response find(@PathParam("login") String login, @PathParam("pass") String pass) {
        Uzytkownik user = new Uzytkownik();

        if (uzytkownikBean.checkSingleUser(login)) {

            user = uzytkownikBean.getUser(login);
            if (user.getPassword().equals(pass)) {
//                uzytkownikBean.updateLastLoginDate(login);
                return Response.ok(user, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/wyszukaj/{fragment}")
    @Produces({"application/json"})
    public List<Uzytkownik> wyszkuaj(@PathParam("fragment") String fragment) {
        return uzytkownikBean.wyszukaj(fragment);
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Uzytkownik> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Uzytkownik> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
