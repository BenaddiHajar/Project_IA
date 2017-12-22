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
			outLayer.add(new Neuron(new Sigmoid())); 
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
			listout[indice]=n.getCurrentOutput();
			indice++;
		}
		o=new Output(listout);
		return o;
	}

	public Map<Integer,Double> train(int nbIterations) {
		Map <Integer,Double> tr=new HashMap<Integer,Double>();
		//feed --> bacpropagration --->test 
		
		for(int i=1;i<=nbIterations;i++){
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
