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
@Path("entitys.gra")
public class GraFacadeREST extends AbstractFacade<Gra> {

    @PersistenceContext(unitName = "WebApplication1PU")
    private EntityManager em;

    @EJB
    private UzytkownikBean uzytkownikBean;

    @EJB
    private GraBean graBean;
    @EJB
    private GraUserBean graUserBean;

    public GraFacadeREST() {
        super(Gra.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(Gra entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    public void edit(@PathParam("id") Long id, Gra entity) {
        entity.setId(id);
        super.edit(entity);
    }

    @PUT
    @Path("/zapiszdecyzje/{decyzja}")
    @Consumes({"application/json"})
    public Response zapiszUzytkownika(@PathParam("decyzja") Integer decyzja, Uzytkownik user) {
        Gra gra = new Gra();
        gra = graBean.wezGraByData(new Date());
        graUserBean.zmienDecyzja(gra, user, decyzja);
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @DELETE
    @Path("wypiszuzytkownika/{id}")
    public Response wypiszUzytkownika(@PathParam("id") Long id) {
        graUserBean.wypiszUzytkownika(graBean.wezGraByData(new Date()), id);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Gra find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Path("/getgra")
    @Produces({"application/json"})
    public Response getGra() {
        Gra gra = new Gra();
        if (!graBean.sprawdzCzyGraja(new Date())) {
            gra.setData(new Date());
            gra.setDodatkoweInformacje("dotatkowe info");
            graBean.add(gra);
        }
        gra = graBean.wezGraByData(new Date());
        return Response.ok(gra).build();
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Gra> findAll() {
        return super.findAll();
    }

//    @GET
//    @Path("{from}/{to}")
//    @Produces({"application/json"})
//    public List<Gra> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
//        return super.findRange(new int[]{from, to});
//    }

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
