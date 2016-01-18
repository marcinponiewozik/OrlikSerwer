/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import beansDB.GraBean;
import beansDB.GraUserBean;
import beansDB.UzytkownikBean;
import entitys.Gra;
import entitys.GraUser;
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
import javax.ws.rs.core.Response;

/**
 *
 * @author Marcin
 */
@Stateless
@Path("grauser")
public class GraUserFacadeREST extends AbstractFacade<GraUser> {

    @PersistenceContext(unitName = "WebApplication1PU")
    private EntityManager em;

    @EJB
    private GraBean graBean;
    @EJB
    private UzytkownikBean uzytkownikBean;
    @EJB
    private GraUserBean graUserBean;

    public GraUserFacadeREST() {
        super(GraUser.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(GraUser entity) {
        super.create(entity);
    }

    @POST
    @Path("/zapiszgra/{id}")
    @Consumes({"application/json"})
    public Response zapiszDecyzje(@PathParam("id") Long id, GraUser graUser) {
        Gra gra = new Gra();
        gra = graBean.wezGraByData(new Date());
        Uzytkownik user = new Uzytkownik();
        user = uzytkownikBean.getUserById(id);
        graUser.setGra(gra);
        graUser.setUzytkownik(user);
        graUserBean.dodaj(graUser);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/listachetnych")
    @Produces({"application/json"})
    public List<Uzytkownik> listaChetnych() {
        Gra gra = new Gra();
        gra = graBean.wezGraByData(new Date());
        return graUserBean.wszystkieDecyzje(gra, 0);
    }

    @GET
    @Path("/listaniezdecydowanych")
    @Produces({"application/json"})
    public List<Uzytkownik> listaNiezdecydowanych() {
        Gra gra = new Gra();
        gra = graBean.wezGraByData(new Date());

        return graUserBean.wszystkieDecyzje(gra, 1);
    }

    @GET
    @Path("/listanieobecni")
    @Produces({"application/json"})
    public List<Uzytkownik> listaNieobecni() {
        Gra gra = new Gra();
        gra = graBean.wezGraByData(new Date());

        return graUserBean.wszystkieDecyzje(gra, 2);
    }

    @DELETE
    @Path("wypiszuzytkownika/{id}")
    public Response wypiszUzytkownika(@PathParam("id") Long id) {
        graUserBean.wypiszUzytkownika(graBean.wezGraByData(new Date()), id);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/uzytkownik/{id}/zapiszdecyzje/{decyzja}")
    @Consumes({"application/json"})
    public Response zmienDecyzje(@PathParam("decyzja") Integer decyzja, @PathParam("id") Long id, Uzytkownik user) {
        Gra gra = new Gra();
        user.setId(id);
        gra = graBean.wezGraByData(new Date());
        graUserBean.zmienDecyzja(gra, user, decyzja);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    public void edit(@PathParam("id") Long id, GraUser entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public GraUser find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<GraUser> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<GraUser> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
