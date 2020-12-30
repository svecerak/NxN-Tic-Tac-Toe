/**
 * This class represents an entry in the dictionary, associating a configuration
 * with its integer score
 * 
 * @author Sasa Vecerak (#250470100)
 *
 */
public class Record {

	private int score;
	private String config;

	/**
	 * A constructor which returns a new Record with the specified configuration and
	 * score The string config will be used as the key attribute for every Record
	 * object
	 * 
	 * @param config config
	 * @param score  score
	 */
	public Record(String config, int score) {
		this.config = config;
		this.score = score;
	}

	/**
	 * Returns the configuration stored in this Record
	 * 
	 * @return configuration stored in Record
	 */
	public String getConfig() {
		return config;
	}

	/**
	 * Returns the score in this Record.
	 * 
	 * @return score stored in Record
	 */
	public int getScore() {
		return score;
	}
}