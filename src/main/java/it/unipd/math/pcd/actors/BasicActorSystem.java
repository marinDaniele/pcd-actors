package it.unipd.math.pcd.actors;

/**
 * Created by Daniele Marin on 26/01/16.
 *
 * Le istanze della seguente classe possono gestire solamente attori
 * con ActorMode LOCAL
 */
public class BasicActorSystem extends AbsActorSystem {
    @Override
    protected ActorRef createActorReference(ActorMode mode) {
        // costruisce e restituisce un ActorRefImpl
        return new ActorRefImpl(this);
    }

    @Override
    public void stop(ActorRef<?> actor) {
        // mi sincronizzo sulla Map actors
        synchronized (actors) {
            // recupero l'Actor relativo al ActorRef
            AbsActor attore = (AbsActor) giveMeActor(actor);
            // Disattivo l'Actor
            attore.deactiveActor();
            // Rimuovo L'Actor dal sistema
            actors.remove(actor);
        }
    }

    @Override
    public void stop() {
        for (ActorRef actorRef : actors.keySet() ) {
            stop(actorRef);
        }

    }
}
