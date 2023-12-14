# Simple Text based Music App
MyAudioApp is a Java-based application designed to manage and interact with various types of audio content such as songs, audiobooks, playlists, and podcasts via a command-line interface.

## Features
- Manage and organize songs, audiobooks, podcasts, and playlists.
- Interactive command-line user interface for performing actions.
- Searching and downloading audio content from the store.
- Error handling with custom exceptions.
- File I/O for reading audio content information from a file (store.txt).

## Usage
1. Clone the repo
2. Compile the Java files: `javac MyAudioUI.java`
3. Run the application: `java MyAudioUI`

## Commands
- `STORE`: Lists all songs, audiobooks, and other content available in the store.
- `SONGS`: Lists all songs in the user's library.
- `BOOKS`: Lists all audiobooks in the user's library.
- `ARTISTS`: Lists all artists available in the user's library.
- `PLAYLISTS`: Lists all playlists available in the user's library.
- `SEARCH`: Search the store for audio content based on the title.
- `SEARCHA`: Search the store for audio content based on the artist/author.
- `SEARCHG`: Search the store for songs based on the genre.
- `SEARCHP`: Search audio content that partially matches a target string.
- `DOWNLOAD`: Download content from the store to the user's library by specifying the index range.
- `DOWNLOADA`: Download content by artist/author from the store to the user's library.
- `DOWNLOADG`: Download songs by genre from the store to the user's library.
- `PLAYSONG`: Play a song by specifying its index in the user's library.
- `BOOKTOC`: Print the table of contents of an audiobook in the user's library.
- `PLAYBOOK`: Play a specific chapter of an audiobook in the user's library.
- `PLAYALLPL`: Play all content in a playlist by specifying its title.
- `PLAYPL`: Play specific content from a playlist by specifying its title and index.
- `DELSONG`: Delete a song from the user's library and playlists.
- `MAKEPL`: Create a new playlist by specifying its title.
- `PRINTPL`: Print the content of a playlist by specifying its title.
- `ADDTOPL`: Add content from the library to a playlist.
- `DELFROMPL`: Delete content from a playlist.
- `SORTBYYEAR`: Sort songs in the library by year.
- `SORTBYNAME`: Sort songs in the library by name.
- `SORTBYLENGTH`: Sort songs in the library by length.
