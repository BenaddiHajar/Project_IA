package ann;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class OneHiddenLayer extends ANN{

	List<Neuron> hiddenLayer;
	int numHiddenNeurons ;

	public OneHiddenLayer(Map<Input, Output> trainingData, Map<Input, Output> testingData,int nbNeuroneH) {
		this.numHiddenNeurons=nbNeuroneH;
		System.out.println(" nb de neurones cachés:"+ numHiddenNeurons);
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
		
		
		hiddenLayer =new ArrayList<Neuron>(numHiddenNeurons);
		for(int i=0; i<numHiddenNeurons;i++){
			hiddenLayer.add(new Neuron(new Sigmoid())); //new Sigmoid() 
		}
		
		//lien des parents de hiddenLAyer
			for(Neuron n :hiddenLayer){
				for(InputNeuron inL :inLayer)
					n.parents.add(inL);
			}
		
		//lien des enfants de hiddenLayer
			for(Neuron n :hiddenLayer){
				for(Neuron outL :outLayer)
					n.children.add(outL);
			}
		
		//lien des parents pour chaque neurone de outlayer 
				for(Neuron n: outLayer){
					for(Neuron hl :hiddenLayer)
					n.parents.add(hl);
				}
				
		//lien des enfants pour chaque neurone inLayer
				for(InputNeuron ip:inLayer){
					for(Neuron hl: hiddenLayer)
						ip.children.add(hl);
				}
		
		
		  //initialisation des poids des outputs
			for(Neuron n :outLayer){
					n.initWeights();
			}
			
		//initialisation des poids de hiddelLayer
			for(Neuron n :hiddenLayer){
				n.initWeights();
			}
	}
	
	public Output feed(Input in){
		Output o;
		double [] listout = new double[10];
		int indice=0;
		//copie des données data dans inlayer
		Iterator<Double> vals=in.iterator();
		for(InputNeuron inpN : inLayer){
			inpN.feed(vals.next());	
	     	//System.out.println(inpN.getCurrentOutput());
		}
		
		//PROPAGATION AVANT	
		// feed sur l'ensemble hiddenLayer
		for(Neuron n :hiddenLayer){
			n.feed();
		}
		//feed sur l'ensemble de outLayer
		for(Neuron n: outLayer){
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
				 
				 double[] valtrainData = out.getVal();
				 int ind=0;
				 
				 for(Neuron n :outLayer){
					n.backPropagateHiddenLayer(valtrainData[ind]);
					ind++;
				 }
				 
				 for(Neuron n :hiddenLayer){
						n.backPropagateHiddenLayer(n.error);
				}  
				 
				 //maj poids
				 for(Neuron n :hiddenLayer){
						n.majPoidHiddenLayer();
				 } 
				 for(Neuron n: outLayer){
					 n.majPoidHiddenLayer();
				 }
				 
			}
			double err = test(testingData,i);
			tr.put(i,err);
		}
		return tr;
	}

}
