package it.unipd.math.pcd.actors;

/**
 * Created by Daniele Marin on 26/01/16.
 *
 * Le istanze della seguente classe possono gestire solamente attori
 * con ActorMode LOCAL
 * Il tentativo di utilizzare altre ActorMode solleva un eccezione
 */
public class BasicActorSystem extends AbsActorSystem {
    @Override
    protected ActorRef createActorReference(ActorMode mode) {
        return null;
    }

    @Override
    public void stop(ActorRef<?> actor) {

    }

    @Override
    public void stop() {

    }
}
