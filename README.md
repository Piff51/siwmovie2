#Siw Movie 2 - La vendetta

Casi d'uso:

UC1 inserimento dati film amministratore
	
attore principale: Amministratore


	scenario principale di successo:
		1.l'amministratore seleziona l'opzione "aggiungi film"
		2.il sistema mostra l'elenco di dati da inserire (titolo, anno, immagine)
		3.l'amministratore compila i campi e preme il bottone "fine"
		4.il sistema mostra un resoconto dei dati inseriti e registra il film

	estensioni
		3.a l'amministratore clicca sul pulsante "indietro" e il sistema annulla l'operazione

UC2 inserimento dati artista amministratore

attore principale: Amministratore


	scenario principale di successo:
		1.l'amministratore seleziona l'opzione "aggiungi artista"
		2.il sistema mostra l'elenco di dati da inserire (nome, cognome, ecc)
		3.l'amministratore compila i campi e preme il bottone "fine"
		4.il sistema mostra un resoconto dei dati inseriti e registra l'artista
		

	estensioni
		3.a l'amministratore clicca sul pulsante "indietro" e il sistema annulla l'operazione

UC3 aggiornamento dati film amministratore

attore principale: Amministratore


	scenario principale di successo:
		1.l'amministratore seleziona l'opzione "modifica film"
		2.il sistema mostra l'elenco di film disponibili
		3.l'amministratore seleziona un film
		4.il sistema mostra i dati del film selezionato
		5.l'ammnistratore preme il tasto "modifica" vicino al campo da modificare
		6.il sistema mostra una pagina per la modifica
		7.l'amministratore effettua la modifica e preme il pulsante "fine"
		8.il sistema mostra il resoconto dei dati modificati

UC4 aggiornamento dati artista amministratore

attore principale: Amministratore


	scenario principale di successo:
		1.l'amministratore seleziona l'opzione "modifica artista"
		2.il sistema mostra l'elenco di artisti disponibili
		3.l'amministratore seleziona un artista
		4.il sistema mostra i dati dell' artista selezionato
		5.l'ammnistratore preme il tasto "modifica" vicino al campo da modificare
		6.il sistema mostra una pagina per la modifica
		7.l'amministratore effettua la modifica e preme il pulsante "fine"
		8.il sistema mostra il resoconto dei dati modificati


UC5 inserimento recensione utente registrato

attore principale: Utente Registrato


	scenario principale di successo:
		1.l'utente registrato seleziona un film
		2.il sistema mostra i dati del film
		3.l'utente preme il pulstante "aggiungi recensione"
		4.il sistema mostra i dati da compilare
		5.l'utente compila i dati e conferma l'inserimento
		6.il sistema aggiunge la recensione al film
	
	estensioni
		5.a l'utente non conferma l'inserimento

UC6 modifica recensione utente registrato

attore principale: Utente Registrato


	scenario principale di successo:
		1.l'utente registrato seleziona un film
		2.il sistema mostra i dati del film compresa la recensione effettuata
		3.l'utente seleziona il tasto modifica affianco alla recensione"
		6.il sistema mostra i campi da modificare
		7.l'utente modifica i dati e conferma l'operazione
		8.il sistema modifica i dati e torna sulla pagine del profilo
		
	estensioni
		7.a l'utente non conferma l'operazione

UC7 visualizzazione film utente generico

attore principale: Utente Generico


	scenario principale di successo:
		1.l'utente generico accede tramite il portale
		2.il sistema mostra un pagina di benvenuto
		3.l'utente generico seleziona "elenco film"
		2.il sistema mostra l'elenco di tutti i film
		3.l'utente seleziona un film
		4.il sistema mostra i dati del film


UC8 visualizzazione attori utente generico

attore principale: Utente Generico


	scenario principale di successo:
		1.l'utente generico accede tramite il portale
		2.il sistema mostra un pagina di benvenuto
		3.l'utente preme il pulsante "elenco artisti"
		4.il sistema mostra un elenco degli artisti
		5.l'utente seleziona un artista
		6.il sistema mostra i dati dell'artista
