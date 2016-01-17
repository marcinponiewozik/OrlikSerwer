/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import beansDB.GraBean;
import beansDB.GraUserBean;
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
@Path("entitys.grauser")
public class GraUserFacadeREST extends AbstractFacade<GraUser> {

    @PersistenceContext(unitName = "WebApplication1PU")
    private EntityManager em;

    @EJB
    private GraBean graBean;
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
    @Path("/zapisz/{id}/decyzja/{decyzja}")
    @Consumes({"application/json"})
    public Response zapiszDecyzje(@PathParam("decyzja") Integer decyzja, @PathParam("id") Long id, Uzytkownik user) {
        Gra gra = new Gra();
        user.setId(id);
        gra = graBean.wezGraByData(new Date());
        GraUser graUser = new GraUser();
        graUser.setDecyzja(decyzja);
        graUser.setGra(gra);
        graUser.setUzytkownik(user);
        super.create(graUser);
//        graUserBean.zapiszDecyzje(gra, user, decyzja);
        return Response.status(Response.Status.CREATED).build();
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
