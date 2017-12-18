package ann;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
public class SingleLayer extends ANN{
	


	public SingleLayer(Map<Input, Output> trainingData, Map<Input, Output> testingData) {
		generator = new Random();
		this.trainingData = trainingData;
		this.testingData = testingData;
		outLayer =new ArrayList <Neuron>(10);
		for(int i=0; i<10;i++){
			outLayer.add(new Neuron(new Linear()));
		}
		inLayer =new ArrayList<InputNeuron>(28*28); 
		 for(int i=0; i<28*28;i++){
		  inLayer.add(new InputNeuron()) ;
		}
		
		//initialisation des parents pour chaque neurone de outlayer 
		for(Neuron n: outLayer){
			n.parents.addAll(inLayer);
		
		}
		//initilisation des enfants pour chaque neurone inLayer
		for(InputNeuron ip:inLayer){
			ip.children.addAll(outLayer);
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
			inpN=new InputNeuron(255);
			inpN.feed(vals.next());
		}
		
		double [] listout = new double[10]; 				 // somme pondérées out calculé de outlayer 
	//	List <Double> poids = new ArrayList<Double>() ;		 // listes des poids associées à un neurone de sortie 
		for(Neuron n :outLayer){
			n.feed();
		    int indice =0;
				//System.out.println("Somme norm :" +n.out);
			listout[indice]=n.getCurrentOutput();
			indice++;
		}
		//pour chaque Neurone de outlayer on reccupere le poids et on lui associe la somme calucler plus bas
/*		for(Neuron n:outLayer){
	       Map<Neuron,Double> p = n.w;
	       for (Map.Entry<Neuron,Double> e :p.entrySet()){
	    	    poids.add(e.getValue());
	    	}
	       //
	       double somme= 0;
	       //1er for : parcours la liste des poid du neurone n de outLayer
			for(Double d :  poids){
				for(InputNeuron inpN:inLayer){
					//voir feed de neurone
			      somme += inpN.out*d; //somme des datas pour chaque output * le poids
				}
			
				
			    somme /=(28*28); //normalisation [0-1]
				///test///
			  //  Linear h=null;
				//n.out=h.activate(somme);
				//test///
*/			   // int indice =0;
			//	System.out.println("Somme norm :" +somme);
			//	listout[indice]=somme;
			//	indice++;
		  //}
		//}
		
		//trouver la plus grande valeur de listOut
				double max=0;
				int indice=0;
				for(int i=0;i<listout.length;i++){
					if(listout[i]>max){
						max=listout[i];
						indice =i;
						System.out.println("Max:" +max);
					}
				}	
			 o=new Output(indice);
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
		
		for(int i=1;i<=nbIterations;i++){
		Input in = null;
		Output out=null;
		
		for (HashMap.Entry<Input, Output> entry : trainingData.entrySet()){
		 in=entry.getKey();
		 out=entry.getValue();
		
		 Output ou = feed(in);
		
		//appel de Backpropagation
		 double[] valtrainData = out.getVal();
		 int ind=0;
		 for(Neuron n :outLayer){
			n.backPropagate(valtrainData[ind]);
			ind++;
		 }
		}
		//appel de test
		double err = test(trainingData,i);
		tr.put(i,err);
		}
	 return tr;
	}
	
}
