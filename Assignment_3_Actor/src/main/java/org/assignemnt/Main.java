package org.assignemnt;

import akka.actor.typed.ActorSystem;
import org.assignemnt.actor.BootActor;
import org.assignemnt.message.MsgBoot;
import org.assignemnt.model.Directory;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        final ActorSystem<MsgBoot> bootActor = ActorSystem.create(BootActor.create(), "boot_actor");

        bootActor.tell(new MsgBoot(new Directory("C:/Users/david/Desktop/TestFolder2"), 1000, 5));

        //Thread.sleep(1000);

    }
}