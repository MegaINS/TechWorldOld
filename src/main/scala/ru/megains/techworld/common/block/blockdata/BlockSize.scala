package ru.megains.techworld.common.block.blockdata

class BlockSize(val length: Byte,val height: Byte,val width: Byte) {



}
object BlockSize{

   def apply(length: Byte,height: Byte,width: Byte): BlockSize ={
       new BlockSize(length,height,width)
   }

}
