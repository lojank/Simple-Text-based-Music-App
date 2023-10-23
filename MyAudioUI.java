import java.util.ArrayList;
import java.util.Scanner;


// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		AudioContentStore store = new AudioContentStore();
		
		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			//try block catches all exceptions for any functions
			try 
			{
				String action = scanner.nextLine();
			
				if (action == null || action.equals("")) 
				{
					System.out.print("\n>");
					continue;
				}
				else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))	//exits program
					return;
				
				else if (action.equalsIgnoreCase("STORE"))	// List all songs, audiobooks in the store
				{
					store.listAll(); 
				}
				else if (action.equalsIgnoreCase("SONGS"))	// List all songs in the library
				{
					mylibrary.listAllSongs(); 
				}
				else if (action.equalsIgnoreCase("BOOKS"))	// List all books in the library
				{
					mylibrary.listAllAudioBooks(); 
				}
				else if (action.equalsIgnoreCase("ARTISTS"))	// List all artists in song ibrary
				{
					mylibrary.listAllArtists(); 
				}
				else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
				{
					mylibrary.listAllPlaylists(); 
				}
				//searchs store for audiocontent based on title input and prints results
				else if (action.equalsIgnoreCase("SEARCH"))
				{
					//gets title to search for in store
					String title = "";				
					System.out.print("Title: ");
					if (scanner.hasNextLine())
					{
						title = scanner.nextLine();
					}
					//prints audiocontent info if title found or error message if not found
					store.search(title);
				} 
				//searchs store for audiocontent based on artist/authour input and prints results
				else if (action.equalsIgnoreCase("SEARCHA"))
				{
					//gets artist/author to search for in store
					String artist = "";				
					System.out.print("Artist: ");
					if (scanner.hasNextLine())
					{
						artist = scanner.nextLine();
					}
					//prints audiocontent info if artist found or error message if not found
					store.searchArtist(artist);
				} 
				//searchs store for audiocontent based on genre input and prints results
				else if (action.equalsIgnoreCase("SEARCHG"))
				{
					//gets genre to search for in store
					String genre = "";				
					System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");
					if (scanner.hasNextLine())
					{
						genre = scanner.nextLine();
					}
					//prints audiocontent info if genre found or error message if not found
					store.searchGenre(genre);
				} 
				//searchs store for audiocontent based on a target string input that searches for any relavent input and prints results
				else if (action.equalsIgnoreCase("SEARCHP"))
				{
					//gets target string to search for in store
					String target = "";				
					System.out.print("Keyword: ");
					if (scanner.hasNext())
					{
						target = scanner.next();
						scanner.nextLine();
					}
					//prints audiocontent info if target string matches any info of audiocontents or error message if no matches found
					store.searchInfo(target);
				} 
				// Download audiocontent (song/audiobook) from the store into the user's library content
				// Specify the index of the content
				else if (action.equalsIgnoreCase("DOWNLOAD")) 
				{
					//gets store index to start downloading from
					int from_index = 0;				
					System.out.print("From Store Content #: ");
					if (scanner.hasNextInt())
					{
						from_index = scanner.nextInt();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
					//gets store index to stop download from the start indx
					int to_index = 0;				
					System.out.print("To Store Content #: ");
					if (scanner.hasNextInt())
					{
						to_index = scanner.nextInt();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}

					//downloads all content specified in the index range
					for (int i=from_index; i<=to_index; i++)
					{
						//seperate try/catch block to catch exception from downloading each audiocontent
						try 
						{
							//downloads audiocontent
							AudioContent content = store.getContent(i);
							mylibrary.download(content);
							//prints error messages if audiocontent not found or audiocontnet already downloaded
						} catch (AudioContentNotFoundException exception) 
						{
							System.out.println(exception.getMessage());
						} catch (DuplicateAudioContentException exception)
						{
							System.out.println(exception.getMessage());
						}
					}	
									
				}
				// Download audiocontent (song/audiobook) from the store into the user's library content based on artist
				// Specify the artist of the content
				else if (action.equalsIgnoreCase("DOWNLOADA")) 
				{
					//gets store index to download
					String artist = "";				
					System.out.print("Artist Name: ");
					if (scanner.hasNextLine())
					{
						artist = scanner.nextLine();
					}
					
					//gets the indicies in the store of audiocontnet of the given artist from the map
					ArrayList<Integer> store_indicies = store.getArtists(artist);
					//iteratres through each index to download
					for (int i=0; i<store_indicies.size(); i++)
					{
						//seperate try/catch block to catch exception from downloading each audiocontent
						try
						{
							//downloads each content
							AudioContent content = store.getContent(store_indicies.get(i)+1);
							mylibrary.download(content);		
						} catch (ArtistNotFoundException exception) 
						{
							System.out.println(exception.getMessage());
						} catch (DuplicateAudioContentException exception)
						{
							System.out.println(exception.getMessage());
						}
					} 
				}
				// Download audiocontent (song/audiobook) from the store into the user's library content based on genre
				// Specify the genre of the content
				else if (action.equalsIgnoreCase("DOWNLOADG")) 
				{
					//gets store index to download
					String genre = "";				
					System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");
					if (scanner.hasNext())
					{
						genre = scanner.next();
						scanner.nextLine();
					}
					
					//gets the indicies in the store of audiocontnet of the given genre from the map
					ArrayList<Integer> store_indicies = store.getGenres(genre);
					for (int i=0; i<store_indicies.size(); i++)
					{		
						//seperate try/catch block to catch exception from downloading each audiocontent	
						try
						{			
							//downloads each content
							AudioContent content = store.getContent(store_indicies.get(i)+1);
							mylibrary.download(content);		
						} catch (GenreNotFoundException exception) 
						{
		
						} catch (DuplicateAudioContentException exception)
						{
							System.out.println(exception.getMessage());
						}
					} 
				}
				// Get the *library* index (index of a song based on the songs list)
				// of a song from the keyboard and play the song 
				else if (action.equalsIgnoreCase("PLAYSONG")) 
				{
					//get library index of song to play
					int index = 0;		
					System.out.print("Song Number: ");
					if (scanner.hasNextInt())
					{
						index = scanner.nextInt();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
					//plays song by printing song info and lyrics if song exists
					// Print error message if the song doesn't exist in the library
					mylibrary.playSong(index);					
					
				}
				// Print the table of contents (TOC) of an audiobook that
				// has been downloaded to the library. Get the desired book index
				// from the keyboard - the index is based on the list of books in the library
				else if (action.equalsIgnoreCase("BOOKTOC")) 
				{
					//gets library index of book
					int index = 0;
					System.out.print("Audio Book Number: ");
					if (scanner.hasNextInt())
					{
						index = scanner.nextInt();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
					//prints table of contents if book exists
					// Print error message if the book doesn't exist in the library
					mylibrary.printAudioBookTOC(index);
					
				}
				// Similar to playsong above except for audio book
				// In addition to the book index, read the chapter 
				// number from the keyboard - see class Library
				else if (action.equalsIgnoreCase("PLAYBOOK")) 
				{
					//get library index of book
					int index = 0;		
					System.out.print("Audio Book Number: ");
					if (scanner.hasNextInt())
					{
						index = scanner.nextInt();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
						
					}
					//get chapter of book selected
					int chapter = 0;
					System.out.print("Chapter: ");
					if (scanner.hasNext())
					{
						chapter = scanner.nextInt();
						scanner.nextLine();
					}
					//plays book chapter if input is valid
					//prints error message if book does not exist in library or chapter does not exist in the selected book
					mylibrary.playAudioBook(index, chapter);
						
				}

				// Specify a playlist title (string) 
				// Play all the audio content (songs, audiobooks, podcasts) of the playlist 
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PLAYALLPL")) 
				{
					//gets playlist title to play
					String pl_title = "";
					System.out.print("Playlist Title: ");
					if (scanner.hasNext())
					{
						pl_title = scanner.next();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
					//prints the lyrics, chapters, episode for each respective audiocontent if input is valid
					//prints error message if playlist does not exist
					mylibrary.playPlaylist(pl_title);

				}
				// Specify a playlist title (string) 
				// Read the index of a song/audiobook/podcast in the playist from the keyboard 
				// Play all the audio content 
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PLAYPL")) 
				{
					//gets playlist title
					String pl_title = "";
					System.out.print("Playlist Title: ");
					if (scanner.hasNext())
					{
						pl_title = scanner.next();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
					//gets the index to play from the selected playlist
					int content_num = 0;
					System.out.print("Playlist Content #: ");
					if (scanner.hasNextInt())
					{
						content_num = scanner.nextInt();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
					//prints the lyrics, chapter, episode for the respective audiocontent selected if input is valid
					//prints error message if playlist does not exist or if content does not exist for the selected playlist
					mylibrary.playPlaylist(pl_title, content_num);
					
						
					
				}
				// Delete a song from the list of songs in mylibrary and any play lists it belongs to
				// Read a song index from the keyboard
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("DELSONG")) 
				{
					//gets library index of song
					int index = 0;
					System.out.print("Library Song #: ");
					if (scanner.hasNextInt())
					{
						index = scanner.nextInt();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}

					//deletes song from library and all playlists if input is valid
					// Print error message if the song doesn't exist in the library
					mylibrary.deleteSong(index);			
					
				}
				// Read a title string from the keyboard and make a playlist
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("MAKEPL")) 
				{
					//gets playlist name to be created
					String plName = "";
					System.out.print("Playlist Title: ");
					if (scanner.hasNext())
					{
						plName = scanner.next();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
					//playlist is created if input is valid
					//prints error message if plalylist already exists
					mylibrary.makePlaylist(plName);
					
				}
				// Print the content information (songs, audiobooks, podcasts) in the playlist
				// Read a playlist title string from the keyboard
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
				{
					//gets playlist name to be created
					String plName = "";
					System.out.print("Playlist Title: ");
					if (scanner.hasNext())
					{
						plName = scanner.next();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
					//prints info for each song, audiobook, podcast contained in the playlist if input  is valid
					//prints error message if playlist does not exist
					mylibrary.printPlaylist(plName);
					
						
					
				}
				// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
				// Read the playlist title, the type of content ("song" "audiobook" "podcast")
				// and the index of the content (based on song list, audiobook list etc) from the keyboard
			// see class Library for the method to call
				else if (action.equalsIgnoreCase("ADDTOPL")) 
				{
					//gets playlist name
					String plName = "";
					System.out.print("Playlist Title: ");
					if (scanner.hasNext())
					{
						plName = scanner.next();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
					//gets content type
					String content_type = "";
					System.out.print("Content Type [SONG, AUDIOBOOK, PODCAST]: ");
					if (scanner.hasNext())
					{
						content_type = scanner.next();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
					//gets the specified audiocontent's library index
					int content_num = 0;
					System.out.print("Library Content #: ");
					if (scanner.hasNextInt())
					{
						content_num = scanner.nextInt();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}

					//adds the specified content to the specified playlist
					//prints error message if playlist, content type, or library index of content does not exist
					mylibrary.addContentToPlaylist(content_type, content_num, plName);
					
						
					

				}
				// Delete content from play list based on index from the playlist
				// Read the playlist title string and the playlist index
			// see class Library for the method to call
				else if (action.equalsIgnoreCase("DELFROMPL")) 
				{
					//gets playlist name
					String pl_title = "";
					System.out.print("Playlist Title: ");
					if (scanner.hasNext())
					{
						pl_title = scanner.next();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
					//gets the audiocontent's index in the playlist
					int content_num = 0;
					System.out.print("Playlist Content #: ");
					if (scanner.hasNextInt())
					{
						content_num = scanner.nextInt();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
					//deletes the content from the specified playlist if input is valid
					//prints error message if playlist or playlist's content does not exist
					mylibrary.delContentFromPlaylist(content_num, pl_title);
					
						
					
				}
				
				else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
				{
					mylibrary.sortSongsByYear();
				}
				else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
				{
					mylibrary.sortSongsByName();
				}
				else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
				{
					mylibrary.sortSongsByLength();
				}

			}
			//catches all exceptions from any methods called from the library or audiocontentstore
			//prints repective error messages
			catch (AudioContentNotFoundException exception)
			{
				System.out.println(exception.getMessage());
			} catch (AudiobookChapterNotFoundException exception)
			{
				System.out.println(exception.getMessage());
			} catch (DuplicatePlaylistException exception)
			{
				System.out.println(exception.getMessage());
			} catch (PlaylistNotFoundException exception)
			{
				System.out.println(exception.getMessage());
			} catch (AudioContentTypeNotFoundException exception)
			{
				System.out.println(exception.getMessage());
			} catch (TitleNotFoundException exception)
			{
				System.out.println(exception.getMessage());
			} catch (ArtistNotFoundException exception)
			{
				System.out.println(exception.getMessage());
			} catch (GenreNotFoundException exception)
			{
				System.out.println(exception.getMessage());
			} catch (NoSearchResultsFoundException exception)
			{
				System.out.println(exception.getMessage());
			}

				System.out.print("\n>");
			
		}
		scanner.close();
	}
}
