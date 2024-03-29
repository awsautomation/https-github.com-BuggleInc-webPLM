package sort.pancake;

import plm.universe.pancake.PancakeWorld
import plm.universe.pancake.PancakeEntity
import plm.core.log.Logger

class ScalaGatesBurnedPancakeEntity extends PancakeEntity {


	override def run() {
		solve();
	}

	/* BEGIN HIDDEN */
	def getRankOf(size:Int ): Int = {
		for (rank <- 0 to getStackSize()-1)
			if (getPancakeRadius(rank) == size)
				return rank;
		return -99; // Well, be robust to border cases 
	}
	
	def isFree(pos: Int):Boolean= {
		if (pos == -99)
			return false;
		val radius = getPancakeRadius(pos);
		if (pos>0) {
			val nextRadius = getPancakeRadius(pos-1);
			if (nextRadius == radius-1 && isPancakeUpsideDown(pos)==false && isPancakeUpsideDown(pos-1)==false || nextRadius == radius+1 && isPancakeUpsideDown(pos)==true && isPancakeUpsideDown(pos-1)==true )
				return false;
		}
		if (pos<getStackSize()-1) {
			val nextRadius = getPancakeRadius(pos+1);
			if (nextRadius == radius-1 || nextRadius == radius+1)
				return false;
		}
		return true;
	}
	def isFirst(pos: Int):Boolean= {
		if (pos == -99)
			return false;
		val radius = getPancakeRadius(pos);
		if (pos>0) {
			val nextRadius = getPancakeRadius(pos-1);
			if (nextRadius == radius-1 && isPancakeUpsideDown(pos)==false && isPancakeUpsideDown(pos-1)==false|| nextRadius == radius+1 && isPancakeUpsideDown(pos)==true && isPancakeUpsideDown(pos-1)==true)
				return false;
		}
		if (pos<getStackSize()-1) {
			val nextRadius = getPancakeRadius(pos+1);
			if (nextRadius == radius-1 && isPancakeUpsideDown(pos)==true && isPancakeUpsideDown(pos+1)==true|| nextRadius == radius+1&& isPancakeUpsideDown(pos)==false && isPancakeUpsideDown(pos+1)==false)
				return true;
		}
		return false;
	}
	def isLast(pos: Int):Boolean= {
		if (pos == -99)
			return false;
		val radius = getPancakeRadius(pos);
		if (pos<getStackSize()-1) {
			val nextRadius = getPancakeRadius(pos+1);
			if (nextRadius == radius-1 && isPancakeUpsideDown(pos)==true && isPancakeUpsideDown(pos+1)==true|| nextRadius == radius+1 && isPancakeUpsideDown(pos)==false && isPancakeUpsideDown(pos+1)==false)
				return false;
		}
		if (pos>0) {
			val nextRadius = getPancakeRadius(pos-1);
			if (nextRadius == radius-1 && isPancakeUpsideDown(pos)==false && isPancakeUpsideDown(pos-1)==false|| nextRadius == radius+1&& isPancakeUpsideDown(pos)==true && isPancakeUpsideDown(pos-1)==true)
				return true;
		}
		return false;
	}
	def blockLength():Int= {
		var pos = 0;
		var radius = getPancakeRadius(pos);
		val o = getPancakeRadius(pos+1) - radius;
		
		if (o != -1 && o != 1) {
			Logger.log("Asked to compute the block length, but the step o is "+o+" instead of +1 or -1. " +
					"The length is then 1, but you are violating a precondition somehow");
			return 1;
		}

		while (pos < getStackSize()-1 && getPancakeRadius(pos+1) == radius + o && isPancakeUpsideDown(pos)==isPancakeUpsideDown(pos+1) && isPancakeUpsideDown(pos)==isPancakeUpsideDown(0)) {
			pos+=1;
			radius += o;
		}
		return pos+1;
	}
	
	val debug=0; // 0: silence; 1: which cases; 2: all details
	/* END HIDDEN */

	/* BEGIN TEMPLATE */
	def solve() {
		/* BEGIN SOLUTION */
		/* cruft to search for an instance exercising all transformations */
		var doneA=false;
		var doneB=false;
		var doneC=false;
		var doneD=false;
		var doneE=false;
		var doneF=false;
		var doneG=false;
		var doneH=false;
		val origSizes = new Array[Integer] (getStackSize());
		for (i <- 0 to getStackSize()-1)
			origSizes(i) = getPancakeRadius(i);
		/* end of this cruft */

		val stackSize = getStackSize();
		
		if (debug>0) {
			System.out.print("{");
			for (rank <- 0 to stackSize -1) 
				System.out.print(""+getPancakeRadius(rank)+", ");
			Logger.log("}");
		}
		
		while(true) {
			val tRadius = getPancakeRadius(0);
			val posTPlus  = getRankOf(tRadius+1); // returns -99 if non-existent, that is then ignored
			val posTMinus = getRankOf(tRadius-1); 
			val posT = 0;
			
			if (debug>1) {
				println("t Radius: "+tRadius);
				for (rank <- 0 to stackSize -1) {
					print("["+rank+"]="+getPancakeRadius(rank)+"; ");

					if (isFree(rank))
						System.out.print("free;");
					else 
						System.out.print("NON-free;");

					if (isFirst(rank))
						System.out.print("first; ");
					else 
						System.out.print("NON-first; ");

					if (isLast(rank))
						System.out.print("last; ");
					else 
						System.out.print("NON-last; ");


					if (rank == posTPlus)
						System.out.print("t+1; ");
					if (rank == posTMinus)
						System.out.print("t-1; ");
					if (rank == posT)
						System.out.print("t;" );

					Logger.log("");
				}
			}

			if (isFree(posT)) {			
				if (isFree(posTPlus)) { /* CASE A: t and t+o free */
					if (debug>0)
						Logger.log("Case A+");
					if(isPancakeUpsideDown(posT)){
						if(isPancakeUpsideDown(posTPlus)){
							flip(posTPlus+1);
							flip(1);
							flip(posTPlus+1);
						}
						flip(posTPlus);
					}else if(!isPancakeUpsideDown(posT)){
						if(isPancakeUpsideDown(posTPlus)){
							flip(posTPlus+1);
							flip(1);
							flip(posTPlus+1);
						}
						flip(1);
						flip(posTPlus);
					}
					doneA = true;
				} else if (isFree(posTMinus)) { /* CASE A: t and t-o free */
					if (debug>0)
						Logger.log("Case A-");
					if(isPancakeUpsideDown(posT) && isPancakeUpsideDown(posTMinus)){
						flip(posTMinus+1);
						flip(1);
						flip(posTMinus);
					}else if(isPancakeUpsideDown(posT) && !isPancakeUpsideDown(posTMinus)){
						flip(posTMinus+1);
						flip(posTMinus);
					}else if(!isPancakeUpsideDown(posT) && isPancakeUpsideDown(posTMinus)){
						flip(1);
						flip(posTMinus+1);
						flip(1);
						flip(posTMinus);
					}else if(!isPancakeUpsideDown(posT) && !isPancakeUpsideDown(posTMinus)){
						flip(1);
						flip(posTMinus+1);
						flip(posTMinus);
					}
					doneA = true;

				} else if (isFirst(posTPlus)) { /* CASE B: t free, t+o first element */
					if (debug>0)
						Logger.log("Case B+");
					if(!isPancakeUpsideDown(posT)){
						flip(1);
					}
					flip(posTPlus);
					doneB = true;
				} else if (isFirst(posTMinus)) { /* CASE B: t free, t-o first element */
					if (debug>0)
						Logger.log("Case B-");
					if(isPancakeUpsideDown(posT)){
						flip(1);
					}
					flip(posTMinus);
					doneB = true;

				} else if (Math.min(posTPlus,posTMinus) != -99) { /* CASE C: t free, but both t+o and t-o are last elements */
					if (debug>0)
						Logger.log("Case C");
					if(!isPancakeUpsideDown(posT) && posTMinus<posTPlus || isPancakeUpsideDown(posT) && posTMinus>posTPlus){
						flip(1);
					}
					flip(Math.min(posTPlus,posTMinus) );
					flip(Math.min(posTPlus,posTMinus) - 1);
					flip(Math.max(posTPlus,posTMinus) + 1);
					flip(Math.min(posTPlus,posTMinus) - 1);
					doneC = true;

				} else {
					if (debug>0)
						Logger.log("Case Cbis");
					if(isPancakeUpsideDown(posT) && posTMinus==(-99) || !isPancakeUpsideDown(posT) && posTPlus==(-99)){
						flip(1);
					}
					flip(Math.max(posTPlus,posTMinus) + 1);
					flip(Math.max(posTPlus,posTMinus) );
					doneC = true;
				}

			} else { // t is in a block
				if (blockLength() == stackSize) { // Done!
					if (tRadius != 1) // all reverse 
						flip(stackSize);
					if (doneA && doneB && doneC && doneD && doneE && doneF && doneG && doneH && world.asInstanceOf[PancakeWorld].wasRandom) {
						Logger.log("BINGO! This instance is VERY interesting as it experiences every cases of the algorithm.\nPLEASE REPORT IT. PLEASE DONT LOSE IT.");
						System.out.print("{");
						for (rank <- 0 to stackSize) 
							System.out.print(""+origSizes(rank)+", ");
						Logger.log("}");
					}
					return;
				}

				if (isFree(posTPlus)) {          /* CASE D: t in a block, t+1 free */
					if (debug>0)
						Logger.log("Case D+");
					if(isPancakeUpsideDown(posTPlus)){
						flip(posTPlus+1);
						flip(1);
						flip(posTPlus+1);
					}
					flip(posTPlus);
					doneD = true;

				} else if (isFree(posTMinus)) {  /* CASE D: t in a block, t-1 free */
					if (debug>0)
						Logger.log("Case D-");
					if(!isPancakeUpsideDown(posTMinus)){
						flip(posTMinus+1);
						flip(1);
						flip(posTMinus+1);
					}
					flip(posTMinus);
					doneD = true;

				} else if (isFirst(posTPlus)) {  /* CASE E: t in a block, t+1 first element */
					if (debug>0)
						Logger.log("Case E+");
					flip(posTPlus);
					doneE = true;

				} else if (isFirst(posTMinus)) { /* CASE E: t in a block, t-1 first element */
					if (debug>0)
						Logger.log("Case E-");
					flip(posTMinus);
					doneE = true;

				}else if (isLast(posTPlus) && posTPlus != 1) { /* CASE F+: t in a block, t+1 last element */
					doneF = true;
					if (debug>0)
						Logger.log("Case F+");
					flip(blockLength());
					flip(posTPlus + 1);
					val newPos = getRankOf(tRadius);
					if (newPos>0)
						flip(newPos);
					
				} else if (isLast(posTMinus) && posTMinus != 1) { /* CASE F-: t in a block, t-1 last element */
					doneF = true;
					if (debug>0)
						Logger.log("Case F-");
					flip(blockLength());
					flip(posTMinus + 1);
					val newPos = getRankOf(tRadius);
					if (newPos>0)
						flip(newPos);
				} else {
					val k = blockLength()-1;
					val o = getPancakeRadius(1) - tRadius;
					val pos = getRankOf(tRadius+(k+1)*o);
					if (isFree(pos) || isFirst(pos)) {
						doneG = true;
						if (debug>0)
							Logger.log("Case G");
						if((isPancakeUpsideDown(pos) && o>0) || (!isPancakeUpsideDown(pos) && o<0)){
							flip(pos+1);
							flip(1);
							flip(pos+1);
						}
						if(k+1 != pos){
							flip(k+1);
							flip(pos);
						}
					} else {
						doneH = true;
						if (debug>0)
							Logger.log("Case H");
						flip(pos+1);
						flip(getRankOf(tRadius+k*o));
					}
				}
			}
		}
		/* END SOLUTION */
	}
	/* END TEMPLATE */

}
