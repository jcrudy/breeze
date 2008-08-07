// THIS IS AN AUTO-GENERATED FILE. DO NOT MODIFY.    
// generated by GenCounter on Thu Aug 07 12:06:16 PDT 2008
package scalanlp.counters.floats;

import scala.collection.mutable.Map;
import scala.collection.mutable.HashMap;

/**
 * Count objects of type Float with type Double.
 * This trait is a wraooer around Scala's Map trait
 * and can work with any scala Map. 
 *
 * @author dlwh
 */
trait Float2DoubleCounter extends DoubleCounter[Float] {


  abstract override def update(k : Float, v : Double) = {

    super.update(k,v);
  }

  // this isn't necessary, except that the jcl MapWrapper overrides put to call Java's put directly.
  override def put(k : Float, v : Double) :Option[Double] = { val old = get(k); update(k,v); old}

  abstract override def -=(key : Float) = {

    super.-=(key);
  }

  /**
   * Increments the count by the given parameter.
   */
   override  def incrementCount(t : Float, v : Double) = {
     update(t,(this(t) + v).asInstanceOf[Double]);
   }

  /**
   * Increments the count associated with Float by Double.
   * Note that this is different from the default Map behavior.
  */
  override def +=(kv: (Float,Double)) = incrementCount(kv._1,kv._2);

  override def default(k : Float) : Double = 0;

  override def apply(k : Float) : Double = super.apply(k);

  // TODO: clone doesn't seem to work. I think this is a JCL bug.
  override def clone(): Float2DoubleCounter  = super.clone().asInstanceOf[Float2DoubleCounter]

  /**
   * Return the Float with the largest count
   */
  override  def argmax() : Float = (elements reduceLeft ((p1:(Float,Double),p2:(Float,Double)) => if (p1._2 > p2._2) p1 else p2))._1

  /**
   * Return the Float with the smallest count
   */
  override  def argmin() : Float = (elements reduceLeft ((p1:(Float,Double),p2:(Float,Double)) => if (p1._2 < p2._2) p1 else p2))._1

  /**
   * Return the largest count
   */
  override  def max : Double = values reduceLeft ((p1:Double,p2:Double) => if (p1 > p2) p1 else p2)
  /**
   * Return the smallest count
   */
  override  def min : Double = values reduceLeft ((p1:Double,p2:Double) => if (p1 < p2) p1 else p2)

  // TODO: decide is this is the interface we want?
  /**
   * compares two objects by their counts
   */ 
  override  def comparator(a : Float, b :Float) = apply(a) compare apply(b);

  /**
   * Return a new Float2DoubleCounter with each Double divided by the total;
   */
  override  def normalized() : Float2DoubleCounter = {
    val normalized = new HashMap[Float,Double]() with Float2DoubleCounter;
    val total : Double = this.total
    if(total != 0.0)
      for (pair <- elements) {
        normalized.put(pair._1,pair._2 / total)
      }
    normalized
  }

  /**
   * Return the sum of the squares of the values
   */
  override  def l2norm() : Double = {
    var norm = 0.0
    for (val v <- values) {
      norm += (v * v)
    }
    return Math.sqrt(norm)
  }

  /**
   * Return a List the top k elements, along with their counts
   */
  override  def topK(k : Int) = Counters.topK[(Float,Double)](k,(x,y) => (x._2-y._2).asInstanceOf[Int])(this);

  /**
   * Return \sum_(t) C1(t) * C2(t). 
   */
  def dot(that : Float2DoubleCounter) : Double = {
    var total = 0.0
    for (val (k,v) <- that.elements) {
      total += get(k).asInstanceOf[Double] * v
    }
    return total
  }

  def +=(that : Float2DoubleCounter) {
    for(val (k,v) <- that.elements) {
      update(k,(this(k) + v).asInstanceOf[Double]);
    }
  }

}


object Float2DoubleCounter {
  import it.unimi.dsi.fastutil.objects._
  import it.unimi.dsi.fastutil.ints._
  import it.unimi.dsi.fastutil.shorts._
  import it.unimi.dsi.fastutil.longs._
  import it.unimi.dsi.fastutil.floats._
  import it.unimi.dsi.fastutil.doubles._


  import scala.collection.jcl.MapWrapper;
  @serializable
  @SerialVersionUID(1L)
  class FastMapCounter extends MapWrapper[Float,Double] with Float2DoubleCounter {
    private val under = new Float2DoubleOpenHashMap;
    def underlying() = under.asInstanceOf[java.util.Map[Float,Double]];
    override def apply(x : Float) = under.get(x);
    override def update(x : Float, v : Double) {
      val oldV = this(x);
      updateTotal(v-oldV);
      under.put(x,v);
    }
  }

  def apply() = new FastMapCounter();

  
}

