# Librarie Audio - Project for AOP (Java)

## Descriere:
Inspirat dupa Spotify, aplicatia mea doreste a fi o "biblioteca audio". 
Aplicația are 3 tipuri de utilizatori:
Utilizatori anonimi : pot folosi doar comenzi precum login, register, exit.
Utilizatori inregistrati: pot folosi urmatoarele comenzi:  1. Search song, 2. Logout, 3. Playlist, 4. Show all songs, 5. Exit .
Administratori: toate comenzile utilizatorilor + promote, demote, audit, show all users, create song.
## Funcționalități
- Autentificare: Utilizatorii pot să-și autentifice conturile existente.
- Înregistrare: Utilizatorii pot să-și creeze conturi noi.
- Deconectare: Utilizatorii pot să se deconecteze din conturile lor.
- Afisarea melodiilor: Utilizatorii pot vedea toate melodiile din baza de date.
- Cautarea unei melodii: In functie de criteriul de cautare (dupa id, nume artist, nume melodie, album, an lansare), utilizatorul poate gasi o melodie.
- Creeare de playlist: Utilizatorii pot creea playlist-uri cu melodiile preferate.
- Export Playlist: Utilizatorii isi pot exporta playlistul (in format .txt).
- Serviciu Audit: Administratorii pot vedea ce comenzi (si output) au folosit alti utilizatori.
- Creeare de melodii: Administratorii pot creea noi melodii.
- Promovare și retrogradare: Administratorii pot să promoveze sau să retrogradeze alți utilizatori.
- Vizualizare utilizatori: Administratorii pot să vadă lista completă a utilizatorilor înregistrați.

## Autentifcare/Inregistrare:

Utilizatorii sunt stocati intr-o baza de date MySQL, pentru simplicitate am optat in a salva parola in text clar.

<img width="289" alt="Screenshot 2024-06-01 at 23 37 53" src="https://github.com/dandragos/AUDIO_Library_PAOPROJ/assets/120018554/67cf4374-282c-4ac0-8536-e4a0c2a3e735">

## UI:
In ceea ce priveste User Interface-ul, aplicatia se desfasoara in linia de comanda, toate comenzile sunt vizibile mereu, deoarece este implementat o interfata de tip meniu.

<img width="289" alt="Screenshot 2024-06-01 at 23 39 28" src="https://github.com/dandragos/AUDIO_Library_PAOPROJ/assets/120018554/75ab266f-b90e-4f95-83dc-dd9427c97edd">

In functie de meniul in care se afla, este implementat si un sistem de paginare.

Ex. comanda show all songs:

<img width="706" alt="Screenshot 2024-06-01 at 23 40 30" src="https://github.com/dandragos/AUDIO_Library_PAOPROJ/assets/120018554/8dfa8964-d1d8-4686-823d-a39ffc1106c6">

## Audit:

Serviciul de audit este adresat adminstratorului, acesta logheaza toate comenzile date de utilizatori si le stocheaza intr-un fisier .JSON

## Stocare/Citire date:

In ceea ce priveste stocarea/citirea datelor avem dupa cum urmeaza:

Songs -> Melodiile sunt stocate intr-un tip de date propriu (.song).
Audit -> .JSON
Users -> MySQL
Playlists -> .JSON
Exported Playlists -> .txt

## Developement setup:
1. Cloneaza acest repository in PC-ul tau. (git clone https://github.com/nume-utilizator/repo.git)
2. Deschide terminalul si navigheaza in directorul proiectului.
3. Ruleaza aplicatia.

## Autor
Dan Dragos-Andrei

Sursa inspiratie proiect: https://teaching.blsalin.dev/pao/proiect/AudioLibrary
Cerinte proiect: https://teaching.blsalin.dev/pao/proiect





