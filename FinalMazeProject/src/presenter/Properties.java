package presenter;

import java.io.Serializable;

/**
 * Holds the properties of the Maze creation.
 * @author Yotam Levy & Tomer Brami
 *
 */
@SuppressWarnings("serial")
public class Properties implements Serializable {
	
	private String searchAlgorithm;
	private String generateAlgorithm;
	private String ui;
	
	/**
	 * CTor to define the model properties
	 * @param solveAlgorithm define if solving a maze with dfs/bfs algo's.
	 * @param generateAlgorithm define if generate maze using the simple/dfs/prim algo's.
	 * @param ui define if using the cli or ui interface
	 * @param numOfThreads number of model's threads
	 */
	public Properties(String solveAlgorithm, String searchAlgorithm, String generateAlgorithm, String ui, int numOfThreads) {
		super();
		this.generateAlgorithm = generateAlgorithm;
		this.searchAlgorithm = searchAlgorithm;
		this.ui = ui;
	}
	
	public Properties() {
	}

	/** Copy CTor */
	public Properties(Properties p){
		this.generateAlgorithm = p.generateAlgorithm;
		this.searchAlgorithm = p.searchAlgorithm;
		this.ui = p.ui;
	}

	public String getSearchAlgorithm() {
		return searchAlgorithm;
	}

	public void setSearchAlgorithm(String searchAlgorithm) {
		this.searchAlgorithm = searchAlgorithm;
	}

	public String getGenerateAlgorithm() {
		return generateAlgorithm;
	}

	public void setGenerateAlgorithm(String generateAlgorithm) {
		this.generateAlgorithm = generateAlgorithm;
	}

	public String getUi() {
		return ui;
	}

	public void setUi(String ui) {
		this.ui = ui;
	}
}
