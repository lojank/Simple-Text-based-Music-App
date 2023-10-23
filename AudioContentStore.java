import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.*;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{
		//holds each song, audiobook and podcast to be used in the store
		private ArrayList<AudioContent> contents; 
		//initialize maps to improve faster searching
		//maps a title of audiocontent to its index in the store
		Map<String,Integer> title_map = new HashMap<String,Integer>();
		//maps a artist of audiocontent to multiple indicies in the store
		Map<String,ArrayList<Integer>> artist_map = new HashMap<String,ArrayList<Integer>>();	
		//maps a genre of audiocontent to multiple indicies in the store
		Map<String,ArrayList<Integer>> genre_map = new HashMap<String,ArrayList<Integer>>();
		//maps a index in the audiocontent store to arraylist of strings containing all the information about the audiocontent
		Map<Integer,ArrayList<String>> info_map = new HashMap<Integer,ArrayList<String>>();
		
		//contructor for audiocontent store
		public AudioContentStore()
		{
			try {
				//reads audiocontent from file and put them into the store
				readContents();
			} catch (IOException e) {
				//exits program if IO exception occured
				System.exit(1);
			}

			//fills map for titles	
			for (int i=0; i<contents.size(); i++)
			{
				//each title is mapped to its store index
				title_map.put(contents.get(i).getTitle(), i);
			}

			//fills map of artists
			for (int i=0; i<contents.size(); i++)
			{
				//uses artist for songs
				if (contents.get(i).getType().equals(Song.TYPENAME))
				{
					//creates key for artist if not already created
					if (artist_map.get(((Song) contents.get(i)).getArtist())==null)
					{
						artist_map.put(((Song) contents.get(i)).getArtist(), new ArrayList<Integer>());
					}
					//adds current index to artist's arraylist
					artist_map.get(((Song) contents.get(i)).getArtist()).add(i);
				}
				//uses author for audiobook
				else
				{
					//creates key for author if not already created
					if (artist_map.get(((AudioBook) contents.get(i)).getAuthor())==null)
					{
						artist_map.put(((AudioBook) contents.get(i)).getAuthor(), new ArrayList<Integer>());
					}
					//adds current index to author's arraylist
					artist_map.get(((AudioBook) contents.get(i)).getAuthor()).add(i);
				}
			}

			//fills map of genre			
			for (int i=0; i<contents.size(); i++)
			{
				//can only map songs
				if (contents.get(i).getType().equals(Song.TYPENAME))
				{
					//creates key for genre if not already created
					if (genre_map.get(((Song) contents.get(i)).getGenre().name())==null)
					{
						genre_map.put(((Song) contents.get(i)).getGenre().name(), new ArrayList<Integer>());
					}
					//adds current index to genre's arraylist
					genre_map.get(((Song) contents.get(i)).getGenre().name()).add(i);
				}
			}

			//fills map of information of an audiocontent in order to search for a parital string
			for (int i=0; i<contents.size(); i++)
			{
				//adds an arraylist of strings containing the info of an audiocontnet to its store index
				info_map.put(i, contents.get(i).info());
			}

		}

		//reads information about audiocontents from the file and creates their respective objects
		private ArrayList<AudioContent> readContents() throws IOException
		{
			//initilize arraylist to add the audiocontents
			contents = new ArrayList<AudioContent>();
			//reads file
			FileReader file = new FileReader("store.txt");
			//use scanner to iterates through each line
			Scanner in = new Scanner(file);
			while (in.hasNextLine())
			{
				//uses first line to check if audiocontent is asong or audiobook
				String text = in.nextLine();
				if (text.equals("SONG"))
				{
					System.out.println("LOADING SONG");
					//stores each attributes of the song object into variables
					String id = in.nextLine();
					String title = in.nextLine();
					int year = Integer.parseInt(in.nextLine());
					int length = Integer.parseInt(in.nextLine());
					String artist = in.nextLine();
					String composer = in.nextLine();
					Song.Genre genre = Song.Genre.valueOf(in.nextLine());
					int lyrics_index = Integer.parseInt(in.nextLine());
					String lyrics = "";
					//iterates through how many lines takes up the lyrics
					for (int i=0; i<lyrics_index; i++)
					{
						lyrics+=(in.nextLine() + "\n");
					}
					//creates song object using song attributes
					contents.add(new Song(title, year, id, Song.TYPENAME, lyrics, length, artist, composer, genre, lyrics));

				}
				else if (text.equals("AUDIOBOOK"))
				{
					System.out.println("LOADING AUDIOBOOK");
					//stores each attributes of the audiobook object into variables
					String id = in.nextLine();
					String title = in.nextLine();
					int year = Integer.parseInt(in.nextLine());
					int length = Integer.parseInt(in.nextLine());
					String author = in.nextLine();
					String narrator = in.nextLine();
					int chapter_num = Integer.parseInt(in.nextLine());
					ArrayList<String> chapterTitles = new ArrayList<String>();
					//iterates through the number of chapters there are to get chapter titles
					for (int i=0; i<chapter_num; i++)
					{
						//adds each chapter title to arraylist
						chapterTitles.add(in.nextLine());
					}
					ArrayList<String> chapters = new ArrayList<String>();
					//iterates through the number of chapters
					for (int i=0; i<chapter_num; i++)
					{
						int chapter_length = Integer.parseInt(in.nextLine());
						String chapter_content = "";
						//iterates through the number of lines each chapter has
						for (int j=0; j<chapter_length; j++)
						{
							chapter_content += (in.nextLine() + "\n");
						}
						//adds each chapter to arraylist
						chapters.add(chapter_content);
					}
					//creates new audiobook object using attributes
					contents.add(new AudioBook(title, year, id, AudioBook.TYPENAME, "", length, author, narrator, chapterTitles, chapters));
				}
			}		
			in.close();
			//returns arraylist of audiocontents for store to use
			return contents;
		
		}
		
		/** 
			Gets content from store
			@param index the index of the content to get from the store list
			@return the content from the store
		*/
		public AudioContent getContent(int index)
		{
			if (index < 1 || index > contents.size())
			{
				throw new AudioContentNotFoundException("Store content # " + index + " not found");
			}
			return contents.get(index-1);
		}
		
		/**
			Lists the contents from the store
		 */
		public void listAll()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.print("" + index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}

		//prints the audiocontent with the specified title
		//@param title title of audiocontent
		public void search(String title)
		{
			//checks if title is in map
			if (title_map.get(title)==null)
			{
				//if title is not in map an error message is printed
				throw new TitleNotFoundException("No matches for " + title);
			}	
			//prints audiocontent info is title is found in map	
			int store_index = title_map.get(title);	
			System.out.print("" + (store_index+1) + ". ");
			contents.get(store_index).printInfo();
		}

		//prints each audiocontent with the specified artist/author
		//@param artist artist of audiocontent
		public void searchArtist(String artist)
		{
			//checks if artist is in map
			if(artist_map.get(artist)==null)
			{
				//if artist is not in map an error message is printed
				throw new ArtistNotFoundException("No matches for " + artist);
			}
			//iterates through each store index the artist is found in
			ArrayList<Integer> store_indicies = artist_map.get(artist);
			for (int i=0; i<store_indicies.size(); i++)
			{
				//prints audiocontent info
				System.out.print("" + (store_indicies.get(i)+1) + ". ");
				contents.get(store_indicies.get(i)).printInfo();
				System.out.println("\n");
			} 
		}

		//prints each audiocontent with the specified genre
		//@param genre genre of audiocontent
		public void searchGenre(String genre)
		{	
			//checks if genre is in map
			if(genre_map.get(genre.trim().toUpperCase())==null)
			{
				//if genre is not in map an error message is printed
				throw new GenreNotFoundException("No matches for " + genre);
			}
			//iterates through each store index the genre is found in
			ArrayList<Integer> store_indicies = genre_map.get(genre.trim().toUpperCase());
			for (int i=0; i<store_indicies.size(); i++)
			{
				//prints audiocontent info
				System.out.print("" + (store_indicies.get(i)+1) + ". ");
				contents.get(store_indicies.get(i)).printInfo();
				System.out.println("\n");
			} 
		}

		//prints each audiocontent the contains target string
		//@param p target string
		public void searchInfo(String p)
		{
			int count = 0;
			//iterates through the info map
			for (int i=0; i<info_map.size(); i++)
			{				
				//checks the arraylist of strings to see if any word starts with the target string
				if (info_map.get(i).stream().filter(x -> x.startsWith(p)).count() > 0)
				{
					//prints audiocontent info if a match is found
					System.out.print("" + (i+1) + ". ");
					contents.get(i).printInfo();
					System.out.println("\n");
					count++;
				}		
			}
			//error message if no matches found
			if (count==0)
			{
				throw new NoSearchResultsFoundException("No results found with keyword: " + p);
			}
		}

		//getter method to return the arraylist of store indicies of the respective artist
		//@param artist 
		public ArrayList<Integer> getArtists(String artist)
		{
			if(artist_map.get(artist)==null)
			{
				throw new ArtistNotFoundException("No matches for " + artist);
			}
			return artist_map.get(artist);			
		}

		//getter method to return the arraylist of store indicies containing the respective genre
		//@param genre
		public ArrayList<Integer> getGenres(String genre)
		{
			if(genre_map.get(genre.toUpperCase())==null)
			{
				throw new GenreNotFoundException("No matches for " + genre);
			}
			return genre_map.get(genre.toUpperCase());			
		}


}
