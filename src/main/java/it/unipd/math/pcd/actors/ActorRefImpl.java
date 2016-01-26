package it.unipd.math.pcd.actors;

/**
 * Created by Daniele Marin on 20/01/16.
 */
public class ActorRefImpl<T extends Message> implements ActorRef{

    /**
     * mantengo un riferimento all'ActorSystem per poter ottenere l'attore
     */
    protected final AbsActorSystem actorSystem;

    /**
     * costruttore ad un parametro
     * @param actorSystem riferimento ad un'istanza di un tipo derivato da AbsActorSystem
     */
    public ActorRefImpl( AbsActorSystem actorSystem ) { this.actorSystem = actorSystem; }


    @Override
    public void send(Message message, ActorRef to) {

    }

    /**
     * Compara un due oggetti
     * @param o riferimento ad un oggetto
     * @return 0 se sono uguali -1 altrimenti
     */
    @Override
    public int compareTo(Object o) {
        return (this == o) ? 0 : -1;
    }
}
