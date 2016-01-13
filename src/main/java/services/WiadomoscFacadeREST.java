/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import beansDB.UzytkownikBean;
import beansDB.WiadomosciBean;
import entitys.Uzytkownik;
import entitys.Wiadomosc;
import java.util.ArrayList;
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
@Path("wiadomosc")
public class WiadomoscFacadeREST extends AbstractFacade<Wiadomosc> {

    @PersistenceContext(unitName = "WebApplication1PU")
    private EntityManager em;

    @EJB
    private WiadomosciBean wiadomosciDB;

    @EJB
    private UzytkownikBean uzytkownikDB;

    public WiadomoscFacadeREST() {
        super(Wiadomosc.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(Wiadomosc entity) {
        super.create(entity);
    }

    @POST
    @Consumes({"application/json"})
    @Path("wyslijWiadomoscDoUzytkownika/{idOdbiorca}/{idAdresat}")
    public Response wyslij(@PathParam("idOdbiorca") Long idOdbiorca,@PathParam("idAdresat") Long idAdresat, Wiadomosc entity) {
        Uzytkownik odbiorca = new Uzytkownik();
        Uzytkownik adresat = new Uzytkownik();
        try {
            odbiorca = uzytkownikDB.getUserById(idOdbiorca);
            adresat = uzytkownikDB.getUserById(idAdresat);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            entity.setOdbiorca(odbiorca);
            entity.setNadawca(adresat);
            wiadomosciDB.dodaj(entity);
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        return Response.status(Response.Status.CREATED).build();

    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    public void edit(@PathParam("id") Long id, Wiadomosc entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @DELETE
    @Path("usunWiadomosciUzytkownika/{id}/{n}")
    public Response usunPrzeczytane(@PathParam("id") Long id, @PathParam("n") Integer N) {
        try {
            wiadomosciDB.usunN_Pierwszych(id, N);
        } catch (Exception e) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Wiadomosc find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Wiadomosc> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Wiadomosc> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("WiadomosciUzytkownika/{id}")
    @Produces({"application/json"})
    public List<Wiadomosc> getWiadomosci(@PathParam("id") Long id) {
        return wiadomosciDB.getWiadomosci(id);
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
