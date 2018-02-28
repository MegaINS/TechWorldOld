package ru.megains.techworld.common.entity

//class EntityTracker(worldServer: WorldServer) {
//
//
//    def sendLeashedEntitiesInChunk(player: EntityPlayerMP, chunkIn: Chunk) {
//        //        val list: util.List[Entity] = Lists.newArrayList[Entity]
//        //        val list1: util.List[Entity] = Lists.newArrayList[Entity]
//        //        import scala.collection.JavaConversions._
//        //        for (entitytrackerentry <- this.trackedEntities) {
//        //            val entity: Entity = entitytrackerentry.getTrackedEntity
//        //            if ((entity ne player) && entity.chunkCoordX == chunkIn.xPosition && entity.chunkCoordZ == chunkIn.zPosition) {
//        //                entitytrackerentry.updatePlayerEntity(player)
//        //                if (entity.isInstanceOf[EntityLiving] && entity.asInstanceOf[EntityLiving].getLeashedToEntity != null) list.add(entity)
//        //                if (!entity.getPassengers.isEmpty) list1.add(entity)
//        //            }
//        //        }
//        //        if (!list.isEmpty) {
//        //            import scala.collection.JavaConversions._
//        //            for (entity1 <- list) {
//        //                player.connection.sendPacket(new SPacketEntityAttach(entity1, entity1.asInstanceOf[EntityLiving].getLeashedToEntity))
//        //            }
//        //        }
//        //        if (!list1.isEmpty) {
//        //            import scala.collection.JavaConversions._
//        //            for (entity2 <- list1) {
//        //                player.connection.sendPacket(new SPacketSetPassengers(entity2))
//        //            }
//        //        }
//    }
//}
