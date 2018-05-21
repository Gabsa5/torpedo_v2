package AI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import game.Board;
import game.BoardCell;

/**
 * This class contains the game's Artificial Intelligence and can be used to predict the computers next move.
 *
 * @author Albert Gregus
 * @version 1.0
 */
public class NeuralNetwork {
	private Float[] FIRSTLAYERWEIGHTS;
	private Float[] SECONDLAYERWEIGHTS;
	private Float[] OUTPUTWEIGHTS;
	private Float[] FIRSTLAYERBIAS;
	private Float[] SECONDLAYERBIAS;
	private Float[] OUTPUTBIAS;
	private int LASTHITTED;
	private boolean ISLASTHITTED;
	

	/**
	 * Construct the object.
	 *
	 * @throws IOException Exception if there is a trouble in reading the weight files.
	 */
	public NeuralNetwork() throws IOException
	{
		setNetwork();
		this.ISLASTHITTED = false;
	}

	/**
	 * Set the network according to the board size.
	 *
	 * @throws IOException Exception if there is a trouble in reading the weight files.
	 */
	public void setNetwork() throws IOException
	{
		loadNetwork("AIWeights/");
	}

	/**
	 * Load the weights from the outer files.
	 *
	 * @param networkPath Path for the weight files.
	 * @throws IOException Exception if there is a trouble in reading the weight files.
	 */
	private void loadNetwork(String networkPath) throws IOException
	{
		loadFirstBias(networkPath);
		loadSecondBias(networkPath);
		loadOutputBias(networkPath);
		loadFirstWeights(networkPath);
		loadSecondWeights(networkPath);
		loadOutputWeights(networkPath);
	}

	/**
	 * Loads the first bias layer.
	 *
	 * @param networkPath Path for the first bias layer file.
	 * @throws IOException Exception if there is a trouble in reading the first bias layer file.
	 */
	private void loadFirstBias(String networkPath) throws IOException
	{
		try
		{
			Scanner inFile1 = new Scanner(new File(networkPath + "/bias1.txt")).useDelimiter("\n");
			List<Float> temps = new ArrayList<Float>();
			while (inFile1.hasNext())
			{
				// find next line
				float token1 = Float.parseFloat(inFile1.next());
				temps.add(token1);
			}
			inFile1.close();
			this.FIRSTLAYERBIAS = temps.toArray(new Float[0]);
		} catch (IOException ioe)
		{
			System.out.println("Trouble reading from the file: " + ioe.getMessage());
		}


	}

	/**
	 * Loads the second bias layer.
	 *
	 * @param networkPath Path for the second bias layer file.
	 * @throws IOException Exception if there is a trouble in reading the second bias layer file.
	 */
	private void loadSecondBias(String networkPath) throws IOException
	{

		try
		{
			Scanner inFile1 = new Scanner(new File(networkPath + "/bias2.txt")).useDelimiter("\n");
			List<Float> temps = new ArrayList<Float>();
			while (inFile1.hasNext())
			{
				// find next line
				float token1 = Float.parseFloat(inFile1.next());
				temps.add(token1);
			}
			inFile1.close();
			this.SECONDLAYERBIAS = temps.toArray(new Float[0]);
		} catch (IOException ioe)
		{
			System.out.println("Trouble reading from the file: " + ioe.getMessage());
		}

	}

	/**
	 * Loads the output bias layer.
	 *
	 * @param networkPath Path for the output bias layer file.
	 * @throws IOException Exception if there is a trouble in reading the output bias layer file.
	 */
	private void loadOutputBias(String networkPath) throws IOException
	{
		try
		{
			Scanner inFile1 = new Scanner(new File(networkPath + "/biasout.txt")).useDelimiter("\n");
			List<Float> temps = new ArrayList<Float>();
			while (inFile1.hasNext())
			{
				// find next line
				float token1 = Float.parseFloat(inFile1.next());
				temps.add(token1);
			}
			inFile1.close();
			this.OUTPUTBIAS = temps.toArray(new Float[0]);
		} catch (IOException ioe)
		{
			System.out.println("Trouble reading from the file: " + ioe.getMessage());
		}

	}

	/**
	 * Loads the first weight layer.
	 *
	 * @param networkPath Path for the first weight layer file.
	 * @throws IOException Exception if there is a trouble in reading first weight layer file.
	 */
	private void loadFirstWeights(String networkPath) throws IOException
	{
		try
		{
			Scanner inFile1 = new Scanner(new File(networkPath + "/weight1.txt")).useDelimiter(" |\n");
			List<Float> temps = new ArrayList<Float>();
			while (inFile1.hasNext())
			{
				// find next line
				float token1 = Float.parseFloat(inFile1.next());
				temps.add(token1);
			}
			inFile1.close();
			this.FIRSTLAYERWEIGHTS = temps.toArray(new Float[0]);
		} catch (IOException ioe)
		{
			System.out.println("Trouble reading from the file: " + ioe.getMessage());
		}

	}

	/**
	 * Loads the second weight layer.
	 *
	 * @param networkPath Path for the second weight layer file.
	 * @throws IOException Exception if there is a trouble in reading the second weight layer file.
	 */
	private void loadSecondWeights(String networkPath) throws IOException
	{
		try
		{
			Scanner inFile1 = new Scanner(new File(networkPath + "/weight2.txt")).useDelimiter(" |\n");
			List<Float> temps = new ArrayList<Float>();
			while (inFile1.hasNext()) {
				// find next line
				float token1 = Float.parseFloat(inFile1.next());
				temps.add(token1);
			}
			inFile1.close();
			this.SECONDLAYERWEIGHTS = temps.toArray(new Float[0]);
		} catch (IOException ioe)
		{
			System.out.println("Trouble reading from the file: " + ioe.getMessage());
		}

	}

	/**
	 * Loads the output weight layer.
	 *
	 * @param networkPath Path for the output weight layer file.
	 * @throws IOException Exception if there is a trouble in reading the output weight layer file.
	 */
	private void loadOutputWeights(String networkPath) throws IOException
	{
		try
		{
			Scanner inFile1 = new Scanner(new File(networkPath + "/weightout.txt")).useDelimiter(" |\n");
			List<Float> temps = new ArrayList<Float>();
			while (inFile1.hasNext())
			{
				// find next line
				float token1 = Float.parseFloat(inFile1.next());
				temps.add(token1);
			}
			inFile1.close();
			this.OUTPUTWEIGHTS = temps.toArray(new Float[0]);
		} catch (IOException ioe)
		{
			System.out.println("Trouble reading from the file: " + ioe.getMessage());
		}

	}

	/**
	 * Compute the argmax of the values.
	 *
	 * @param values Values which the argmax is computed from.
	 * @return The index of the maximum value.
	 */
	private int argMax(Float[] values)
	{
		int index = 0;
		Float maxValue = (float) -100.0;
		for(int i=0;i<(values.length); i++)
		{
			if (values[i] > maxValue)
			{
				maxValue = values[i];
				index = i;
			}
		}
		return index;
	}

	/**
	 * Tangent hyperbolic of the value.
	 *
	 * @param value Value which the tangent hyperbolic is computed from.
	 * @return Tangent hyperbolic of the value.
	 */
	private float tanh(Float value)
	{
		return (float) java.lang.Math.tanh(value);
	}

	/**
	 * Compute the output of the specific layer.
	 *
	 * @param layerInputs Inputs of the layer.
	 * @param layerWeights Weights of the layer.
	 * @param layerBias Biases of the layer.
	 * @param tanH The layer should/shouldn't use tanh nonlinearity for the result.
	 * @param layerSize Size of the layer.
	 * @return Output of the layer.
	 */
	private Float[] layerOutput(Float[] layerInputs, Float[] layerWeights, Float[] layerBias, boolean tanH, int layerSize)
	{
		List<Float> temps = new ArrayList<Float>(layerSize);
		float token1 = 0;
		int weightIndex = 0;
		for(int i = 0; i<layerSize; i++)
		{
			for (int j = 0; j<layerInputs.length; j++)
			{
				weightIndex = i + j*layerSize;
				token1 += layerInputs[j] * layerWeights[weightIndex];

			}
			float value = token1 + layerBias[i];
			if(tanH)
			{
				value = tanh(value);
			}
			temps.add(value);
			token1 = 0;

		}

		Float[] firstLayerOutput = temps.toArray(new Float[0]);
		return firstLayerOutput;
	}

	/**
	 * Compute the output of the network.
	 *
	 * @param input input o the network.
	 * @return Output of the network.
	 */
	private Float[] output(Float[] input)
	{
		Float[] firstLayerOutput = layerOutput(input, this.FIRSTLAYERWEIGHTS, this.FIRSTLAYERBIAS, true, 128);
		Float[] secondLayerOutput = layerOutput(firstLayerOutput, this.SECONDLAYERWEIGHTS, this.SECONDLAYERBIAS, true, 128);
		Float[] output = layerOutput(secondLayerOutput, this.OUTPUTWEIGHTS, this.OUTPUTBIAS, false, 100);
		if(this.ISLASTHITTED)
		{
			if(this.LASTHITTED>0)
			{
				output[this.LASTHITTED-1] = output[this.LASTHITTED-1] * (float)1.5;
			}
			if(this.LASTHITTED<99)
			{
				output[this.LASTHITTED+1] = output[this.LASTHITTED-1] * (float)1.5;
			}
			if(this.LASTHITTED>9)
			{
				output[this.LASTHITTED-10] = output[this.LASTHITTED-10] * (float)1.5;
			}
			if(this.LASTHITTED<89)
			{
				output[this.LASTHITTED+10] = output[this.LASTHITTED+10] * (float)1.5;
			}
		}
		
		for (int i = 0; i<input.length; i++)
		{
			if(input[i]==-1 || input[i]==1)
			{
				output[i] = (float) -99;
			}
		}
	
		
		return output;
	}

	/**
	 * Computes the next step for the given board.
	 *
	 * @param board Input board.
	 * @return Next step's index.
	 */
	public int nextStep(Board board)
	{
		if(lifeLeft(board)<4)
		{
			return lastSteps(board);
		}
		int shootIndex = argMax(output(boardToAIBoard(board)));
		if(!board.getCells().get(shootIndex).getIsEmptyCell() && !board.getCells().get(shootIndex).getIsShootedCell())
		{
			this.ISLASTHITTED = true;
			this.LASTHITTED = shootIndex;
		}		
		
		return shootIndex;
	}

	private Float[] boardToAIBoard(Board board) {
		Float[] aiBoard = new Float[board.getBoardSizeSQ()];

		for(BoardCell cell: board.getCells()) {
			if (cell.getIsShootedCell() && !cell.getIsEmptyCell()) {
				aiBoard[cell.getCellIndex()] = 1f;
			} else if (!cell.getIsShootedCell()) {
				aiBoard[cell.getCellIndex()] = 0f;
			} else {
				aiBoard[cell.getCellIndex()] = -1f;
			}
		}

		return aiBoard;
	}
	
	/**
	 * Shoot the last shoots.
	 * 
	 * @param board Input board
	 * @return Shot cell's index
	 */
	private int lastSteps(Board board)
	{
		int index=0;
		while(index<100)
		{
			if(!board.getCells().get(index).getIsEmptyCell() && 
					!board.getCells().get(index).getIsShootedCell())
			{
				return index;
			}
			index++;
		}
		return index;
	}
	
	/**
	 * Counts the remaining lives.
	 * 
	 * @param board Input board
	 * @return Remaining lives
	 */
	private int lifeLeft(Board board)
	{
		int life=0;
		for(int index=0; index<100; index++)
		{
			if(!board.getCells().get(index).getIsEmptyCell() && 
					!board.getCells().get(index).getIsShootedCell())
			{
				life++;
			}
			
		}
		return life;
	}

}