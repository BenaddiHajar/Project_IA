package ann;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
/*
 * La classeNeuron mod�lise un neurone. Chaque neurone n aura deux listes : la liste des neurones
 * "sources", i.e. les neurones qui enverront les entr�es � n et la liste des neurones "destinataires", i.e.
 * les neurones qui utilisent la valeur de sortie de n.!!!
 */
public class Neuron {
	/** name of the neuron */
	String name;	
	/** List of parent neurons, i.e. the list of neurons that are used as input */
	List<Neuron> parents;
	/** list of child neurons,i.e. the list of neurons that use the output of this neuron */
	List<Neuron> children;
	/** activation function */
	Activation h;
	/** weight of the input neurons: it maps an input neuron to a weight */
	Map<Neuron,Double> w;
	/** value of the learning rate (taux d'apprentissage) */
	final static double eta = 0.01;	
	/** current value of the output of the neuron */
	protected double out;
	/** current value of the error of the neuron */
	protected double error;
	/** random number generator */
	public static Random generator;
	
	/** 
	 * returns the current value of the error for that neuron.
	 * @return
	 */
	public double getError(){return error;}
	
	/**
	 * Constructor
	 * @param h is an activation function (linear, sigmoid, tanh)
	 */
	public Neuron(Activation h){
		if (generator==null)
			generator = new Random();
		this.h=h;
		parents = new ArrayList<>();
		children = new ArrayList<>();	
		w = new HashMap<Neuron,Double>();
	}
	
	public void addParent(Neuron parent){
		parents.add(parent);
	}
	
	public void addChild(Neuron child){
		children.add(child);
	}
	
	/**
	 * Initializes randomly the weights of the incoming edges 
	 */
	public void initWeights(){
		double min = -0.5;
		double max= 0.5;
		double r ;
		//initialise le poids des arretes entrantes 
		for(Neuron n: parents){
		   r= min+(max-min)*generator.nextDouble();
		   //System.out.println(r);
			w.put(n,r);
		}
	}
	
	/**
	 * Computes the output of a neuron that is either in the hidden layer or in the output layer. 
	 * (there are no arguments as the neuron is not an inputNeuron)
	 */
	public void feed(){
		//la somme pondérée de ses entrées// utilisation de ses parents 
		double somP=0;
		for( Neuron n: parents){
			somP +=  w.get(n)*n.getCurrentOutput();
			//System.out.println(w.get(n)+"*"+n.getCurrentOutput());
		}
		out= h.activate(somP);
	}
	
	
	/**
	 * back propagation for a neuron in the output layer 
	 * @param val is the correct value.
	 */
	public void backPropagate(double target){
		//calcul erreur pour un neurone 
		error=(target-this.out);		
		double somme =0;
		 
		//mise à jour du poids output du neurone 
		double poidsMaj=0;
		for( Neuron o: parents){
			//System.out.println("poid avant"+w.get(o));
			poidsMaj=w.get(o)+(eta*(error)*o.getCurrentOutput()); 
			w.put(o,poidsMaj);
		   //System.out.println("t -o" +(this.out)+" " +o.getCurrentOutput());
		   // System.out.println("poid MAJ"+ w.get(o));
		}
	}	
	

	
	public void backPropagateHiddenLayer(double target){
		//error=(target-this.out);
		double somme=0;
	   
		//si la liste des enfants est vide alors c'est un neurone de outLayer
		if(children.isEmpty()){
			error= this.getCurrentOutput()*(1-this.getCurrentOutput())*(target-this.getCurrentOutput());
		}
		//sinon c'est un hidden
		else if(children.isEmpty()==false && parents.isEmpty()==false){
			for(Neuron n : children){
				somme += n.w.get(this)*n.error;
			}
			error= this.getCurrentOutput()*(1-this.getCurrentOutput())* somme;
		}
	
		
	}
	
	public void majPoidHiddenLayer(){
		//mise à jour du poids output du neurone 
		double poidsMaj=0;
		for( Neuron o: parents){
			poidsMaj=w.get(o)+(eta*(error)*o.getCurrentOutput()); 
			w.put(o,poidsMaj);
		}
	}
	
	
	
	/**
	 * returns the current ouput (it should be called once the output has been computed, 
	 * i.e. after calling feed)
	 * @return the current value of the ouput
	 */
	public double getCurrentOutput(){
		return out;
	}
	
	/** returns the name of the neuron *
	 * 
	 */
	public String toString(){
		return name + " out: " + out ;
	}
	

}
