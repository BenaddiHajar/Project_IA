package ann;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.text.html.HTMLDocument.HTMLReader.HiddenAction;
public class SingleLayer extends ANN{

	public SingleLayer(Map<Input, Output> trainingData, Map<Input, Output> testingData) {
		generator = new Random();
		this.trainingData = trainingData;
		this.testingData = testingData;
		outLayer =new ArrayList <Neuron>(10);
		for(int i=0; i<10;i++){
			outLayer.add(new Neuron(new Sigmoid())); //new Sigmoid() 
		}
		inLayer =new ArrayList<InputNeuron>(28*28); 
		for(int i=0; i<28*28;i++){
		 inLayer.add(new InputNeuron(255)) ;
		}
		
		//lien des parents pour chaque neurone de outlayer 
		for(Neuron n: outLayer){
			for(InputNeuron inN :inLayer)
			n.parents.add(inN);
		}
		//lien des enfants pour chaque neurone inLayer
		for(InputNeuron ip:inLayer){
			//ip.children.addAll(outLayer);
			for(Neuron nN: outLayer)
				ip.children.add(nN);
		}
		
		//initialisation des poids des outputs
		for(Neuron n :outLayer){
			n.initWeights();
		}
		
		//initialisation des poids pour hiddenLayer
		
		
		
	}
	/**
	 * Calcule la sortie du réseau de neurones compte tenu de l'entree Input in
	 */
	public Output feed(Input in){
		Output o;
		//copie des datas dans inLayer et normalisation 
		Iterator<Double> vals=in.iterator();
		for(InputNeuron inpN : inLayer){
			inpN.feed(vals.next());	
	     	//System.out.println(inpN.getCurrentOutput());
		}
		
		int indice =0;
		double [] listout = new double[10]; // somme pondérées out calculé pour outlayer  
		for(Neuron n :outLayer){
			n.feed();
			//System.out.println("Somme norm :" +n.out);
			listout[indice]=n.getCurrentOutput();
			indice++;
		}
		o=new Output(listout);
		return o;
	}
	/**
	*méthode qui entraîne le réseau de neurones pour un certain nombre d'itérations (aucun test de convergence n'est utilisé).
	* @param numIterations est le nombre d'itérations, c'est-à-dire le nombre de fois que l'algorithme sera mis à jour en utilisant
	* l'ensemble des données d'entraînement
	* @return renvoie la dynamique de l'erreur: il contient une carte qui associe un numéro d'itération et le
	* nombre d'erreurs effectuées sur l'ensemble de test.
	*/
	public Map<Integer,Double> train(int nbIterations) {
		Map <Integer,Double> tr=new HashMap<Integer,Double>();
		//feed --> bacpropagration --->test 
		
		for(int i=0;i<nbIterations;i++){
		Input in = null;
		Output out=null;
		
		for (HashMap.Entry<Input, Output> entry : trainingData.entrySet()){
		 in=entry.getKey();
		 out=entry.getValue();
		
		 feed(in);
		
		//appel de Backpropagation
		 double[] valtrainData = out.getVal();
		 int ind=0;
		 for(Neuron n :outLayer){
			n.backPropagate(valtrainData[ind]);
			ind++;
		 }
		 
	 
		 
		}
		//appel de test
		double err = test(testingData,i);
		tr.put(i,err);
		}
	 return tr;
	}
	
}
