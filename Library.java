import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 


	//constructor that define new empty library
	public Library()
	{
		//each arraylist hold their specified contents in the user's library
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */



	public void download(AudioContent content)
	{
		//checks if content to download is a song
		if (content.getType() == Song.TYPENAME)
		{
			//convert the audiocontent into a song
			Song s = (Song) content;
			//checks if song arraylist already has the song
			if (songs.contains(s))
			{
				throw new DuplicateAudioContentException("SONG " + s.getTitle() + " already downloaded");
				//if song is already contained then error message is printed
			}
			//if song is not contained then song is added to song library
			songs.add(s);
			System.out.println("SONG " + s.getTitle() + " Added to Library");
		}
		//checks if content to download is a audiobook
		else 
		{
			//convert the audiocontent into a audiobook
			AudioBook b = (AudioBook) content;
			//checks if audiobook arraylist already has the audiobook
			if (audiobooks.contains(b))
			{
				//if audiobook is already contained then error message is printed
				throw new DuplicateAudioContentException("AUDIOBOOK " + b.getTitle() + " already downloaded");
			}
			//if audiobook is not contained then it is added to audiobook library
			audiobooks.add(b);
			System.out.println("AUDIOBOOK " + b.getTitle() + " Added to Library");
		}	
	}
	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		//iterates through each song in the library
		for (int i = 0; i < songs.size(); i++)
		{
			//prints the index in the song library and the song info
			int index = i + 1;
			System.out.print("" + index + ". ");
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		//iterates through each audiobook in the library
		for (int i = 0; i < audiobooks.size(); i++)
		{
			//prints the index in the audiobook library and the audiobook info
			int index = i + 1;
			System.out.print("" + index + ". ");
			audiobooks.get(i).printInfo();
			System.out.println();	
		}
	}
	
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		//iterates through each podcast in the library
		for (int i = 0; i < playlists.size(); i++)
		{
			//prints the index in the playlsit library and the playlist title
			int index = i + 1;
			System.out.println("" + index + ". " + playlists.get(i).getTitle());
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arrayl ist is complete, print the artists names
		ArrayList<String> artist_names = new ArrayList<String>();
		//iterates thorugh the songs array list
		for (int i=0; i<songs.size(); i++)
		{
			//checks if the artist name at the current indexalready exists in the arraylist
			if (artist_names.indexOf(songs.get(i).getArtist())==-1)
			{
				//if artist is not already addded it gets added
				artist_names.add(songs.get(i).getArtist());
			}
		}
		//prints each artist and the index once artist arraylist is finished
		for (int i=0; i<artist_names.size(); i++)
		{
			System.out.println(i+1 + ". " + artist_names.get(i));
		}
		
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public void deleteSong(int index)
	{
		//checks if song index is valid
		if (index>=1 && index<=songs.size())
		{
			//iterate through each playlist in the playlists array
			for (int i=0; i<playlists.size();i++)
			{
				//iterates through each content that each playlist contains
				for (int j=0; j<playlists.get(i).getContent().size(); j++)
				{
					//checks if the song if equal to the content in the playlist
					if(songs.get(index-1).equals(playlists.get(i).getContent().get(j)))
					{
						//removes song if the specified song was found
						playlists.get(i).getContent().remove(j);
					}
				}
			}
			//finally removes the song from the songs arraylist
			songs.remove(index-1);
		}
		else
		{
			//if the song index was invalid then error message is printed
			throw new AudioContentNotFoundException("Song number '"+ index + "' not found");
		}
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort() 
		Collections.sort(songs, new SongYearComparator());
	
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{
		public int compare(Song s1, Song s2)
		{
			//checks if s2 is older than s1
			if(s1.getYear() > s2.getYear()) return 1;
			//checks if s1 is older then s2
			if(s1.getYear() < s2.getYear()) return -1;
			//returns no change if song year are equal
			return 0;
		}
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
	 // Use Collections.sort() 
	 Collections.sort(songs, new SongLengthComparator());
	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		public int compare(Song s1, Song s2)
		{
			//checks if s1 is longer than s2
			if(s1.getLength() > s2.getLength()) return 1;
			//checks if s2 is longer than s1
			if(s1.getLength() < s2.getLength()) return -1;
			//returns no change if song length are equal
			return 0;
		}
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  // Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code
		Collections.sort(songs);
	}

	
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	//@param index index of song in songs arraylist
	public void playSong(int index)
	{
		//checks if index is valid
		if (index < 1 || index > songs.size())
		{
			//prints error message if index was not valid
			throw new AudioContentNotFoundException("Song number '"+ index + "' not found");
		}
		else
		{
			//plays the specified song's lyrics if index was valid
			songs.get(index-1).play();
		}
	}
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter)
	{
		//checks if index was found in audiobook arraylist
		if (index < 1 || index > audiobooks.size())
		{
			throw new AudioContentNotFoundException("Audiobook number '" + index + "' not found");
		}
		//checks if chapter exists for specifed audiobook
		if (chapter < 1 || chapter > audiobooks.get(index-1).getNumberOfChapters())
		{
			throw new AudiobookChapterNotFoundException("Chapter '" + chapter + "' not found");
		}
		//sets the current chapter for the audiobook
		audiobooks.get(index-1).selectChapter(chapter);
		//prints the chapter title and contents
		audiobooks.get(index-1).play();
	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	// @param index index of audiobook in library
	public void printAudioBookTOC(int index)
	{
		//checks if index was found in audiobook arraylist
		if (index < 1 || index > audiobooks.size())
		{
			//if the index was not found an error message is printed
			throw new AudioContentNotFoundException("Audiobook number '" + index + "' not found");
		}
		else
		{
			//if index was found the chapters of the audiobook are printed
			audiobooks.get(index-1).printTOC();
		}
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	// @title title of playlist to be made
	public void makePlaylist(String title)
	{
		//makes new playlist object using title
		Playlist new_pl = new Playlist(title);
		//if playlists arraylist is empty the new playlist is added
		if (playlists.size()==0)
		{
			playlists.add(new_pl);
			return;
		}
		else
		{
			//if playlists arraylist is not empty then checks if a playlist of the same title already exists
			for (int i=0; i<playlists.size(); i++)
			{
				if (title.equals(playlists.get(i).getTitle()))
				{
					//if a playlist of the same title already exists an error message is printed
					throw new DuplicatePlaylistException("Playlist '" + title +  "'' Already Exists");
				}
			}
		}
		//if a playlist of the same title does not exist then the playlist is added to the playlists arraylist
		playlists.add(new_pl);
	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	//@param title title of playlist to print
	public void printPlaylist(String title)
	{
		//iterates through each playlist in the playlists arraylist
		for  (int i=0; i<playlists.size(); i++)
		{
			//checks if playlist of the same input parameter title exists
			if(playlists.get(i).getTitle().equals(title))
			{
				//content of specifed playlist is printed if playlist found
				playlists.get(i).printContents();
				return;
			}
		}
		//prints error message if playlist is not found
		throw new PlaylistNotFoundException("Playlist '" + title + "'not found");
	}
	
	// Play all content in a playlist
	//@param playlistTitle title of playlist to play
	public void playPlaylist(String playlistTitle)
	{
		//iterates through each playlist in the playlists arraylist
		for  (int i=0; i<playlists.size(); i++)
		{
			//checks if playlist with the same input parameter title exists
			if(playlists.get(i).getTitle().equals(playlistTitle))
			{
				//play each content in the playlist
				playlists.get(i).playAll();
				return;
			}
		}
		//prints error message if playlist did not exist in the playlist arraylist
		throw new PlaylistNotFoundException("Playlist '" + playlistTitle + "'not found");
	}
	
	// Play a specific song/audiobook in a playlist
	//@param playlistTitle title of playlist to play content from
	//@param indexInPL index of content to play in playlist
	public void playPlaylist(String playlistTitle, int indexInPL)
	{
		//iterates through each playlist in the playlists arraylist
		for  (int i=0; i<playlists.size(); i++)
		{
			//checks if playlist with the same input parameter title exists
			if(playlists.get(i).getTitle().equals(playlistTitle))
			{
				//checks if the index of the content in the specified playlist is valid
				if (playlists.get(i).contains(indexInPL))
				{
					//if the index is valid the content gets played
					playlists.get(i).play(indexInPL);
					return;
				}
				else
				{
					//prints error message if index is not valid
					throw new AudioContentNotFoundException("Index '" + indexInPL + "'not found in playlist + '" + playlistTitle + "'");
				}
				
			}
		}
		//prints error message if playlist with the same input parameter title did not exist
		throw new PlaylistNotFoundException("Playlist '" + playlistTitle + "'not found");
	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	//@param type type of audiocontent to be added
	//@param playlistTitle title of playlist to look into
	//@param index index of content in the specified type's library
	public void addContentToPlaylist(String type, int index, String playlistTitle)
	{
		//checks if playlist name is valid
		int pl_index=0;
		boolean valid_pl = false;
		//iterates through playlists arraylist to find the playlist with the same input parameter playlistTitle
		for  (int i=0; i<playlists.size(); i++)
		{
			if(playlists.get(i).getTitle().equals(playlistTitle))
			{
				valid_pl = true;
				//saves index of playlist
				pl_index =  i;
			}
		}

		if (valid_pl==false)
		{
			//prints error message if playlist does not exist
			throw new PlaylistNotFoundException("Playlist '" + playlistTitle + "'not found");
		}

		//checks if input parameter type was valid
		if (!type.equalsIgnoreCase(Song.TYPENAME) && !type.equalsIgnoreCase(AudioBook.TYPENAME))
		{
			//prints error message if aa valid type was not entered
			throw new AudioContentTypeNotFoundException("Type '" + type + "' not found");
		}

		if(type.equalsIgnoreCase("SONG"))
		{
			//checks if song index in library is valid
			if(index<1  || index > songs.size())
			{
				throw new AudioContentNotFoundException("Song index '" + index + "' not found");
			}
			else
			{
				//adds content to playlist if index was valid
				playlists.get(pl_index).addContent(songs.get(index-1));
				return;
			}
		}

		else if(type.equalsIgnoreCase("AUDIOBOOK"))
		{
			//checks if audiobook index in library is valid
			if(index<1  || index > audiobooks.size())
			{
				throw new AudioContentNotFoundException("Audiobook index '" + index + "' not found");
			}
			else
			{
				//adds content to playlist if index was valid
				playlists.get(pl_index).addContent(audiobooks.get(index-1));
				return;
			}
		}

	}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid
	//@param title title of playlist to delete content from
	//@param index index of content in playlist to delete
	public void delContentFromPlaylist(int index, String title)
	{
		//iterates through playlists arraylist to find the playlist with the same input parameter title
		for  (int i=0; i<playlists.size(); i++)
		{
			if(playlists.get(i).getTitle().equals(title))
			{
				//check if index is found in the specified playlist
				if (!playlists.get(i).contains(index))
				{
					//prints error message if index was not found
					throw new AudioContentNotFoundException("Index '" + index + "'not found in playlist + '" + title + "'");
				}
				else
				{
					//delete content from playlist if index was found
					playlists.get(i).deleteContent(index);
					return;
				}
				
			}
		}
		//prints error message if playlist does not exist
		throw new PlaylistNotFoundException("Playlist '" + title + "'not found");
	}
	
}

//exception classes to produce error messages

class DuplicateAudioContentException extends RuntimeException
{
	public DuplicateAudioContentException(String message)
	{
		super(message);
	}
}

class AudioContentNotFoundException extends RuntimeException
{
	public AudioContentNotFoundException(String message)
	{
		super(message);
	}
}

class AudiobookChapterNotFoundException extends RuntimeException
{
	public AudiobookChapterNotFoundException(String message)
	{
		super(message);
	}
}

class DuplicatePlaylistException extends RuntimeException
{
	public DuplicatePlaylistException(String message)
	{
		super(message);
	}
}

class PlaylistNotFoundException extends RuntimeException
{
	public PlaylistNotFoundException(String message)
	{
		super(message);
	}
}

class AudioContentTypeNotFoundException extends RuntimeException
{
	public AudioContentTypeNotFoundException(String message)
	{
		super(message);
	}
}

class TitleNotFoundException extends RuntimeException
{
	public TitleNotFoundException(String message)
	{
		super(message);
	}
}

class ArtistNotFoundException extends RuntimeException
{
	public ArtistNotFoundException(String message)
	{
		super(message);
	}
}

class GenreNotFoundException extends RuntimeException
{
	public GenreNotFoundException(String message)
	{
		super(message);
	}
}

class NoSearchResultsFoundException extends RuntimeException
{
	public NoSearchResultsFoundException(String message)
	{
		super(message);
	}
}