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
		//initialise le poids des arretes entrantes 
		for(Neuron n: parents){
			w.put(n,generator.nextDouble());
		}
	}
	
	/**
	 * Computes the output of a neuron that is either in the hidden layer or in the output layer. 
	 * (there are no arguments as the neuron is not an inputNeuron)
	 */
	public void feed(){
		// to be completed//la somme pondérée de ses entrées// utilisation de ses parents 
		double somP=0;
		for( Neuron n: parents){
			somP +=  w.get(n)*n.getCurrentOutput();
		}
		//appel de la fonction d'activation.
		out= h.activate(somP);
	}
	
	
	/**
	 * back propagation for a neuron in the output layer 
	 * @param val is the correct value.
	 */
	public void backPropagate(double target){
		//propagation de retour pour un neurone dans la couche de sortie
		//val est la valeur correcte
		/**
		 * n est le nombre d'observation de la base d'apprentissage --> nombre couche cachéé????
		 * //calcul de l'erreur du neurones --> erreur moyenne quadratique 
		 */
		//calcul du delta erreur pour un neurone 
		double somme =0;
		double delta = eta * (target-this.out);
		
		// retropropagation vers les parents du neurones  <--
		
		// Mise a jours du poids vers output --> One HiddenLayer 
		/*for(Neuron o :children){
			somme += w.get(o)*o.getError();
		}
	
		delta*=somme;*/
		error=delta;
		
		//mise à jour du poids output du neurone 
		for( Neuron o: parents){
				//double poidsMaj=(w.get(o)+eta)*(o.out*error);
			double poidsMaj=(w.get(o)+eta)*(target-this.out);
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
