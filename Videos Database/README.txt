	Punctul central al proiectului este main-ul si se apeleaza CallCommands, 
apoi metoda potrivita in functie de tipul actiunii. Din fiecare metoda se 
apeleaza urmatoarele metode din celelalte clase specifice tipului actiunii
(se gasesc in pachetele command, query, recommendation) si afiseaza rezultatul.

Pachetul auxiliary, clasa AuxMethods:
	- metoda sort: sorteaza un map crescator / descrescator dupa chei, apoi
		sorteaza dupa valoarea double; adauga primele n elemente intr-o lista
        si o returneaza;
	- metodele nrYearsMovie/nrYearsSerial: numara cate filme/seriale au aparut
        in unul din anii din lista;
	- metodele nrGenresMovie/nrGenresSerial: numara cate filme/seriale sunt de
        unul dintre genurile din lista;

Pachetul auxiliary, clasa CallCommands:
	- exista cate o metoda pentru fiecare din cele trei actiuni ale unui utili-
        zator, iar dupa executia actiunii respective, se returneaza rezultatul;

Pachetul command, clasa Command:
	- metoda addFavorite: se cauta user-ul si se adauga in lista de favorite 
        video-ul dat;
	- metoda addView: se cauta user-ul si se incrementeaza nr de vizualizari al
        video-ului dat;
	- metoda addRating: se cauta user-ul si se verifica daca este film sau 
        serial, apoi se adauga rating-ul, daca nu a fost dat unul inainte;

Pachetul query, clasa Actor:
	- metoda average: pentru fiecare actor se parcurge lista video-urilor in
        care a jucat si se aduna rating-urile si se adauga intr-un map media;
	- metoda award: pentru fiecare actor se parcurge lista de premii cerute, 
        iar daca actorul are toate acele premii se adauga intr-un map;
	- metoda filter: pentru fiecare actor se formeaza o lista cu cuvintele din
        descriere si parcurge lista de cuvinte cerute, iar daca descrierea le 
        contine pe toate, se adauga intr-un map;

Pachetul query, clasa Users:
	- metoda numRatings: se parcurge lista de useri, iar daca a dat vreun 
        rating se adauga intr-un map;

Pachetul query, clasa Videos:
	* pentru fiecare metoda se parcurge lista de filme/seriale si se verifica 
        daca au anul si genul cerut;
	- metodele ratingMovies/ratingSerial: se adauga in map daca au conditiile;
	- metodele favoriteMovie/favoriteSerial: se creeaza o lista cu video-urile
        ce contin criteriile, apoi se formeaza un map cu numarul de aparitii 
        in listele favorite;
	- metodele longestMovie/longestSerial: se adauga in map daca au conditiile;
	- metodele mostViewedMovie/mostViewedSerial: se creeaza o lista cu video
        ce contin criteriile, apoi se formeaza un map cu nr de vizualizari;
	* in CallCommands se apeleaza o metoda din AuxMethods pentru a sorta 
        asc/desc fiecare map;

Pachetul recommendation, clasa AuxRecommendation:
	* contine doua campuri, un nume si un numar ce reprezinta rating-ul;
	- metoda swap: interschimba valorile a doua obiecte AuxRecommendations;
	- metoda verifyBasic: verifica daca user-ul are varianta basic sau premium;

Pachetul recommendation, clasa Recommendations:
	- metoda allFilms: creeaza o lista cu toate filmele si serialele din baza;
	- metoda standard: se parcurge lista de useri si lista cu toate video-urile
        si se returneaza primul nevazut de user;
	- metoda bestUnseen: se creeaza o lista de tipul AuxRecommendation si se 
        sorteaza descrescator, apoi se returneaza primul nevazut de user;
	- metoda popular: se creeaza un map cu cele mai populare genuri si folosind
        metoda sortDescAMap din AuxMethods se formeaza o lista cu un clasament
        al genurilor si se returneaza primul video de acel gen nevazut de user;
	- metoda favorite: se creeaza un map cu videourile din favorite si folosind
        metoda sortDescAMap se formeaza o lista cu un clasament al video-urilor
		si se returneaza primul video nevazut de user;
	- metoda search: se creeaza un map cu toate video-urile de genul cerut si
        folosind metoda sortAscAMap se formeaza o lista cu un clasament al 
        video-urilor, apoi se parcurge aceasta lista si daca video-ul nu a fost
        vazut se adauga in lista finala ce va fi returnata;

    Sortarile din clasa AuxMethods au la baza implementarea:
https://howtodoinjava.com/java/sort/java-sort-map-by-values/

    Am incercat sa fac o implementare cat mai modularizata, impartita cat mai 
mult in pachete in functie de functionalitate.
    Ar fi fost un lucru de folos daca aveam o explicatie cu forma exacta 
afisarilor (adica propozitiile sau cuvintele ce trebuie afisate), asa a trebuit
sa vad toate cazurile in fisierele ref.