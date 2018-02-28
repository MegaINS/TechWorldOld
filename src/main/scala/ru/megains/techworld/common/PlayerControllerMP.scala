package ru.megains.techworld.common

import ru.megains.techworld.client.TechWorldClient
import ru.megains.techworld.common.EnumActionResult.EnumActionResult
import ru.megains.techworld.common.block.Block
import ru.megains.techworld.common.block.blockdata.{BlockDirection, BlockPos}
import ru.megains.techworld.common.entity.player.{EntityPlayer, EntityPlayerSP}
import ru.megains.techworld.common.item.itemstack.ItemStack
import ru.megains.techworld.common.item.{Item, ItemBlock}
import ru.megains.techworld.common.utils.Vec3f
import ru.megains.techworld.common.world.World

class PlayerControllerMP(oc: TechWorldClient/*, val connection: NetHandlerPlayClient*/) {
//    def setGameType(gameType: GameType) = {
//        currentGameType = gameType
//        //currentGameType.configurePlayerCapabilities(this.mc.thePlayer.capabilities)
//    }


    var isHittingBlock: Boolean = false
    var blockHitDelay: Int = 0
  //  var currentGameType: GameType = GameType.CREATIVE
    private var currentPlayerItem: Int = 0

//    def clickBlockCreative(playerController: PlayerControllerMP, pos: BlockPos /*, facing: EnumFacing*/) {
//        // if (!mcIn.theWorld.extinguishFire(mcIn.thePlayer, pos, facing))
//        playerController.onPlayerDestroyBlock(pos)
//    }

    var currentBlock: BlockPos = _

//    def onPlayerDestroyBlock(pos: BlockPos): Boolean = {
//        //        if (this.currentGameType.isAdventure) {
//        //            if (this.currentGameType eq GameType.SPECTATOR) return false
//        //            if (!this.mc.thePlayer.isAllowEdit) {
//        //                val itemstack: ItemStack = this.mc.thePlayer.getHeldItemMainhand
//        //                if (itemstack == null) return false
//        //                if (!itemstack.canDestroy(this.mc.theWorld.getBlockState(pos).getBlock)) return false
//        //            }
//        //        }
//        //  val stack: ItemStack = mc.thePlayer.getHeldItemMainhand
//        //    if (stack != null && stack.item != null && stack.item.onBlockStartBreak(stack, pos, mc.thePlayer)) return false
//        //  if (this.currentGameType.isCreative && this.mc.thePlayer.getHeldItemMainhand != null && this.mc.thePlayer.getHeldItemMainhand.item.isInstanceOf[ItemSword]) false
//        //   else {
//        val world: World = oc.world
//        //    val iblockstate: IBlockState = world.getBlockState(pos)
//        val block: Block = world.getBlock(pos)
//        //  if ((block.isInstanceOf[BlockCommandBlock] || block.isInstanceOf[BlockStructure]) && !this.mc.thePlayer.func_189808_dh) false
//        //   else
//        if (block eq Blocks.air) false
//        else {
//            //  world.playEvent(2001, pos, Block.getStateId(iblockstate))
//            //   currentBlock = new BlockPos(currentBlock.getX, -1, currentBlock.getZ)
//            //                if (!this.currentGameType.isCreative) {
//            //                    val itemstack1: ItemStack = this.mc.thePlayer.getHeldItemMainhand
//            //                    if (itemstack1 != null) {
//            //                        itemstack1.onBlockDestroyed(world, iblockstate, pos, this.mc.thePlayer)
//            //                        if (itemstack1.stackSize <= 0) {
//            //                            net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(this.mc.thePlayer, itemstack1, EnumHand.MAIN_HAND)
//            //                            this.mc.thePlayer.setHeldItem(EnumHand.MAIN_HAND, null.asInstanceOf[ItemStack])
//            //                        }
//            //                    }
//            //                }
//            val flag: Boolean = block.removedByPlayer(world, pos, oc.player, willHarvest = false)
//            if (flag) block.onBlockDestroyedByPlayer(world, pos)
//            flag
//        }
//        //  }
//    }


//    def createClientPlayer(world: World): EntityPlayerSP = {
//        new EntityPlayerSP(oc, world, connection)
//    }

//    def flipPlayer(playerIn: EntityPlayer) {
//        playerIn.rotationYaw = -180.0F
//    }

//    def clickBlock(loc: BlockPos, face: BlockDirection): Boolean = {
//        //        if (currentGameType.isAdventure) {
//        //            if (currentGameType eq GameType.SPECTATOR) return false
//        //            if (!mc.thePlayer.isAllowEdit) {
//        //                val itemstack: ItemStack = mc.thePlayer.getHeldItemMainhand
//        //                if (itemstack == null) return false
//        //                if (!itemstack.canDestroy(mc.theWorld.getBlockState(loc).getBlock)) return false
//        //            }
//        //        }
//        //  if (!mc.theWorld.getWorldBorder.contains(loc)) false
//        //   else {
//        if (currentGameType.isCreative) {
//            connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, loc, face))
//            // if (!net.minecraftforge.common.ForgeHooks.onLeftClickBlock(mc.thePlayer, loc, face, net.minecraftforge.common.ForgeHooks.rayTraceEyeHitVec(mc.thePlayer, getBlockReachDistance + 1)).isCanceled)
//            clickBlockCreative(this, loc)
//            blockHitDelay = 5
//        }
//        //            else if (!isHittingBlock || !isHittingPosition(loc)) {
//        //                if (isHittingBlock) connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, currentBlock, face))
//        //                connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, loc, face))
//        //                val event: PlayerInteractEvent.LeftClickBlock = net.minecraftforge.common.ForgeHooks.onLeftClickBlock(mc.thePlayer, loc, face, net.minecraftforge.common.ForgeHooks.rayTraceEyeHitVec(mc.thePlayer, getBlockReachDistance + 1))
//        //                val iblockstate: IBlockState = mc.theWorld.getBlockState(loc)
//        //                val flag: Boolean = iblockstate.getMaterial ne Material.AIR
//        //                if (flag && curBlockDamageMP == 0.0F) if (event.getUseBlock ne net.minecraftforge.fml.common.eventhandler.Event.Result.DENY) iblockstate.getBlock.onBlockClicked(mc.theWorld, loc, mc.thePlayer)
//        //                if (event.getUseItem eq net.minecraftforge.fml.common.eventhandler.Event.Result.DENY) return true
//        //                if (flag && iblockstate.getPlayerRelativeBlockHardness(mc.thePlayer, mc.thePlayer.worldObj, loc) >= 1.0F) onPlayerDestroyBlock(loc)
//        //                else {
//        //                    isHittingBlock = true
//        //                    currentBlock = loc
//        //                    currentItemHittingBlock = mc.thePlayer.getHeldItemMainhand
//        //                    curBlockDamageMP = 0.0F
//        //                    stepSoundTickCounter = 0.0F
//        //                    mc.theWorld.sendBlockBreakProgress(mc.thePlayer.getEntityId, currentBlock, (curBlockDamageMP * 10.0F).toInt - 1)
//        //                }
//        //            }
//        true
//        //  }
//    }

    def resetBlockRemoving() {
        //        if (isHittingBlock) {
        //            connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, currentBlock, BlockDirection.DOWN))
        //            isHittingBlock = false
        //            curBlockDamageMP = 0.0F
        //            mc.theWorld.sendBlockBreakProgress(mc.thePlayer.getEntityId, currentBlock, -1)
        //            mc.thePlayer.resetCooldown()
        //        }
    }


//    def onPlayerDamageBlock(posBlock: BlockPos, directionFacing: BlockDirection): Boolean = {
//        //  syncCurrentPlayItem()
//        if (blockHitDelay > 0) {
//            blockHitDelay -= 1
//            true
//        }
//        else if (currentGameType.isCreative /* && mc.theWorld.getWorldBorder.contains(posBlock)*/ ) {
//            blockHitDelay = 5
//            connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, posBlock, directionFacing))
//            clickBlockCreative(this, posBlock)
//            true
//        }
//        //        else if (isHittingPosition(posBlock)) {
//        //            val iblockstate: IBlockState = mc.theWorld.getBlockState(posBlock)
//        //            val block: Block = iblockstate.getBlock
//        //            if (iblockstate.getMaterial eq Material.AIR) {
//        //                isHittingBlock = false
//        //                false
//        //            }
//        //            else {
//        //                curBlockDamageMP += iblockstate.getPlayerRelativeBlockHardness(mc.thePlayer, mc.thePlayer.worldObj, posBlock)
//        //                if (stepSoundTickCounter % 4.0F == 0.0F) {
//        //                    val soundtype: SoundType = block.getSoundType
//        //                    mc.getSoundHandler.playSound(new PositionedSoundRecord(soundtype.getHitSound, SoundCategory.NEUTRAL, (soundtype.getVolume + 1.0F) / 8.0F, soundtype.getPitch * 0.5F, posBlock))
//        //                }
//        //                stepSoundTickCounter += 1
//        //                if (curBlockDamageMP >= 1.0F) {
//        //                    isHittingBlock = false
//        //                    connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, posBlock, directionFacing))
//        //                    onPlayerDestroyBlock(posBlock)
//        //                    curBlockDamageMP = 0.0F
//        //                    stepSoundTickCounter = 0.0F
//        //                    blockHitDelay = 5
//        //                }
//        //                mc.theWorld.sendBlockBreakProgress(mc.thePlayer.getEntityId, currentBlock, (curBlockDamageMP * 10.0F).toInt - 1)
//        //                true
//        //            }
//        //        }
//        else clickBlock(posBlock, directionFacing)
//    }

    def processRightClickBlock(player: EntityPlayerSP, worldIn: World, stack: ItemStack, pos: BlockPos, facing: BlockDirection, vec: Vec3f): EnumActionResult = {
        syncCurrentPlayItem()
        val f: Float = (vec.x - pos.x.toDouble).toFloat
        val f1: Float = (vec.y - pos.y.toDouble).toFloat
        val f2: Float = (vec.z - pos.z.toDouble).toFloat
        var flag: Boolean = false
        //    if (!this.mc.theWorld.getWorldBorder.contains(pos)) EnumActionResult.FAIL
        //   else {

        var result: EnumActionResult = EnumActionResult.PASS
       // if (currentGameType ne GameType.SPECTATOR) {
            val item: Item = if (stack == null) null
            else stack.item
            val ret: EnumActionResult = if (item == null) EnumActionResult.PASS
            else item.onItemUseFirst(stack, player, worldIn, pos, facing, f, f1, f2)
            if (ret ne EnumActionResult.PASS) return ret
            val block: Block = worldIn.getBlock(pos).block
            val bypass: Boolean = true
            //                for (s <- Array[ItemStack](player.getHeldItemMainhand, player.getHeldItemOffhand)) //TODO: Expand to more hands? player.inv.getHands()?
            //                    bypass = bypass && (s == null || s.item.doesSneakBypassUse(s, worldIn, pos, player))
            if (!player.isSneaking || bypass) {
                flag = block.onBlockActivated(worldIn, pos, player, stack, facing, f, f1)
                if (flag) result = EnumActionResult.SUCCESS
            }
            if (!flag && stack != null && stack.item.isInstanceOf[ItemBlock]) {
                val itemblock: ItemBlock = stack.item.asInstanceOf[ItemBlock]
               // if (!itemblock.canPlaceBlockOnSide(worldIn, pos, facing, player, stack)) return EnumActionResult.FAIL
            }
       // }
       // connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, oc.blockSelectPosition, facing, f, f1, f2))
        if (!flag /*&& (currentGameType ne GameType.SPECTATOR)*/) {
            if (stack == null){
                EnumActionResult.PASS
                //  else if (player.getCooldownTracker.hasCooldown(stack.item)) EnumActionResult.PASS
            } else {
                //                if (stack.item.isInstanceOf[ItemBlock] && !player.func_189808_dh) {
                //                 //  val block: Block = stack.item.asInstanceOf[ItemBlock].block
                //                   // if (block.isInstanceOf[BlockCommandBlock] || block.isInstanceOf[BlockStructure]) return EnumActionResult.FAIL
                //                }
                // if (currentGameType.isCreative) {
                //val i: Int = stack.getMetadata
                val j: Int = stack.stackSize
                val enumactionresult: EnumActionResult = stack.onItemUse(player, worldIn, oc.blockSelectPosition, facing, f, f1, f2)
                //    stack.setItemDamage(i)
                stack.stackSize = j
                enumactionresult

                //  }
                // else {
                //     stack.onItemUse(player, worldIn, oc.blockSelectPosition, facing, f, f1, f2)
                // }
            }
        }
        else EnumActionResult.SUCCESS
        //  }
    }

    def processRightClick(player: EntityPlayer, worldIn: World, stack: ItemStack): EnumActionResult = //if (this.currentGameType eq GameType.SPECTATOR) EnumActionResult.PASS
  //  else
    {
        syncCurrentPlayItem()
      //  connection.sendPacket(new CPacketPlayerTryUseItem())
        // if (player.getCooldownTracker.hasCooldown(stack.item)) EnumActionResult.PASS
        //  else {
        val i: Int = stack.stackSize
        val actionresult: ActionResult[ItemStack] = stack.useItemRightClick(worldIn, player)
        val itemstack: ItemStack = actionresult.result
        if ((itemstack ne stack) || itemstack.stackSize != i) {
            player.setHeldItem(itemstack)
            if (itemstack.stackSize <= 0) {
                player.setHeldItem(null)
            }
        }
        actionresult.`type`
        // }
    }

    private def syncCurrentPlayItem() {
        val i: Int = oc.player.inventory.stackSelect
        if (i != currentPlayerItem) {
            currentPlayerItem = i
          //  connection.sendPacket(new CPacketHeldItemChange(currentPlayerItem))
        }
    }

    def windowClick(x: Int, y: Int, button: Int, player: EntityPlayer): Unit = {
        player.openContainer.mouseClicked(x, y, button, player)
      //  connection.sendPacket(new CPacketClickWindow(x, y, button))
    }
}
