import java.util.ArrayList;
import java.util.Scanner;

/*
 *  Audio Content contains information common to all types of audio (e.g. songs, audiobooks etc)
 */

abstract public class AudioContent
{
	//defines audiocontent variables
	private String title;			
	private int year; 				// year published
	private String id;				// id
	private String type;			// 
	private String audioFile; // file containing the audio content - e.g. a song.
	private int length; 			// minutes
	
	/** 
		Audiocontent object contructor with no parameters
	*/
	public AudioContent()
	{
		this.title = "";
		this.year = 0;
		this.id = "";
		this.type = "AUDIOCONTENT";
		this.audioFile = "";
		this.length = 0;
	}
	
	/** 
		Audiocontent object contructor with given parameters
		@param title title of audiocontent
		@param year year audiocontent was made
		@param id id of audiocontent
		@param type type of audiocontent (parent class for songs, audiobook, podcast types of audiocontent)
		@param audiofile content of audiocontent e.g. lyrics, chapter, episode
		@param length lemgth of audiocontent in minutes
	*/
	public AudioContent(String title, int year, String id, String type, String audioFile, int length)
	{
		this.title = title;
		this.year = year;
		this.id = id;
		this.type = type;
		this.audioFile = audioFile;
		this.length = length;
	}

	// Subclasses define their type (e.g. "Song") 
	abstract public String getType();

	// Print Information 
  public void printInfo()
  {
    System.out.println("Title: " + title + " Id: " + id + " Year: " + year + " Type: " + type + " Length: " + length);	 
  }
	
  // Play the content via the audio file
	public void play()
	{
		this.printInfo();
		// Simulate playing of the audio file. For example, for a song this would be printing the lyrics
		System.out.println("\n" + audioFile + "\n");
	}

	//used to create info map
	//returns arraylist of info related to the audiocontent
	public ArrayList<String> info()
	{
		ArrayList<String> s = new ArrayList<String>();
		Scanner seperator = new Scanner(title);
		//seperates strings of multiple words to add one at a time into the arraylist
		while (seperator.hasNext())
		{
			s.add(seperator.next());
		}
		s.add(id);
		s.add(String.valueOf(year));
		s.add(type);
		s.add(String.valueOf(length));
		seperator.close();
		return s;
	}
	
	// Two AudioContent objects are equal if they have the same title and id
	// @param other second object to compare
	// @return whether the obejcts are equal
	public boolean equals(Object other)
	{
		AudioContent otherCon = (AudioContent) other;
		//compares tiles and id
		return title.equals(otherCon.title) && id.equals(otherCon.id);
	}
  
	public String getAudioFile()
	{
		return this.audioFile;
	}

	public void setAudioFile(String file)
	{
		this.audioFile = file;
	}

	public int getLength()
	{
		return this.length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}

	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public int getYear()
	{
		return this.year;
	}

	public void setYear(int year)
	{
		this.year = year;
	}

	public String getId()
	{
		return this.id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}

}
