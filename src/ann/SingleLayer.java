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
		List<Neuron> outLayer =new ArrayList <Neuron>(10);
		for(Neuron n:outLayer){
			Neuron nout =new Neuron(new Linear());
		}
		List<InputNeuron> inLayer =new ArrayList<InputNeuron>(28*28); 
		
		//initialisation des poids des outputs
		for(Neuron n :outLayer){
			n.initWeights();
		}
		//creation des liens --> pour chaque InputNeuron de inLayer on le lie a la liste de 10 Neuron de outLayer
		Map<InputNeuron, List<Neuron>> lien=new HashMap<InputNeuron,List<Neuron>>();
		for(InputNeuron inN:inLayer){
			lien.put(inN, outLayer);
		}
		
		//initilisation a 0 Input et output de trainingData (contruire les listes)
		/*for(int i=0; i<60000;i++){
		Input input=new Input(28);
		Output output=new Output(0); ;
		 this.trainingData.put(input, output);
		        for (int row=0; row<(28); row++){
		        	for(int col=0;col<28;col++){
		        		input.setValue(row, col, 0);
		        	}
		        }
		      //initialisation des poids de output
		      
		    }*/
	}
	/**
	 * Calcule la sortie du réseau de neurones compte tenu de l'entree Input in
	 */
	public Output feed(Input in){
		Output o;
		double [] listout = new double[0];
		//les poids d'un neurone de sortie 
		List <Double> poids = new ArrayList<Double>() ;
		//pour chaque Neurone de outlayer on reccupere le poids et on lui associe la somme calucler plus bas
		for(Neuron n:outLayer){
	       Map<Neuron,Double> p = n.w;
	       for (Map.Entry<Neuron,Double> e :p.entrySet()){
	    	    poids.add(e.getValue());
	    	}
	       //
	       double somme= 0;
	       //1er for : parcours la liste des poid du neurone n de outLayer
	       //2eme et 3eme for parcourir les datas  et reccuperer les valeurs  Input 
			for(Double d :  poids){
				for(int i=0;i<28;i++){
					for(int j=0 ;j<28;j++){
						somme += in.getValue(i,j)*d; //somme des datas * le poids
					}
				}
				int indice =0;
				somme /=(28*28); //normalisation [0-1]
				System.out.println("Somme norm :" +somme);
				listout[indice]=somme;
				indice++;
		   }
	 }
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
			 o =new Output(indice);
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
		// to be completed
		//feed --> bacpropagration --->test 
		Input in = null;
		for (HashMap.Entry<Input, Output> entry : trainingData.entrySet()){
		 in=entry.getKey();
		}
		Output ou = feed(in);
		
		
		
		
		
	 return tr;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*double nbrerreur=0;
	for (int i =0;i<nbIterations;i++){
		for (Neuron o:this.outLayer){
			o.initWeights();
			o.feed();
			o.toString();
			
		}
		for (Neuron o:this.inLayer){
			o.backPropagate(255);
			nbrerreur +=o.getError();
			o.toString();
		}
		
		tr.put(i, nbrerreur);
		nbrerreur=0;
		
	}
	//test(new, nbIterations);
	return tr;*/

}
