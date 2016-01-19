package it.unipd.math.pcd.actors;

/**
 * Created by Daniele Marin on 19/01/16.
 */
public class ActorRefImpl<T extends Message> implements ActorRef {

    protected final AbsActorSystem sys;

    /**
     * Costruttore ad un parametro
     * @param sys è un riferimento ad un istanza di tipo derivato da AbsActorSystem
     */
    public ActorRefImpl(AbsActorSystem sys) { this.sys = sys; }


    @Override
    public void send(Message message, ActorRef to) {
        /**
         * spedisce il messaggio a un actor inserendolo nella sua mailBox
         *
         */
    }

    @Override
    public int compareTo(Object o) {
        return (this == o) ? 0 : -1;
    }
}
