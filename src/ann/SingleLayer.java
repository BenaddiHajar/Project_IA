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
		this.outLayer=new ArrayList <Neuron>();
		this.inLayer= new ArrayList <InputNeuron>();
				// to be completed
	
	}
	
	public Output feed(Input in){
		// to be completed
		return null;
	}
	/**
	*méthode qui entraîne le réseau de neurones pour un certain nombre d'itérations (aucun test de convergence n'est utilisé).
	* @param numIterations est le nombre d'itérations, c'est-à-dire le nombre de fois que l'algorithme sera mis à jour en utilisant
	* l'ensemble des données d'entraînement
	* @return renvoie la dynamique de l'erreur: il contient une carte qui associe un numéro d'itération et le
	* nombre d'erreurs effectuées sur l'ensemble de test.
	*/
	public Map<Integer,Double> train(int nbIterations) {
		for (int i =0;i<nbIterations;i++){
			
		}
		
		
		// to be completed
		return null;
	}
	

}
