# Treść zadania

Należy wykonać prostą, statyczną stronę WWW na dowolny temat, do której zostaną wykorzystane wybrane funkcjonalności SASS, takie jak:

- Zmienne - użyłam chociaż raz (Klaudia)
- Zagnieżdżenia - użyłam chociaż raz (nawet wielokrotne zagnieżdżanie) (Klaudia)
- Mixins
- Dziedziczenie - użyłam raz (Klaudia)
- Operatory
- inne, które mają sens w implementowanym przypadku

Na wysokość oceny będzie miał wpływ jakość strony oraz celowość doboru danej funkcjonalności SASS do rozwiązania implementowanego problemu.
Katalog funkcjonalności nie jest zamknięty, a raczej jest podpowiedzią do tego z czego korzystać.
Proszę dobrze zaprojektować strukturę folderów i plików SCSS, aby w łatwy sposób nimi zarządzać.

# Strukturyzowanie plików robiłam wg

https://www.sitepoint.com/architecture-sass-project/

# Opis plików

- base/\_reset.scss - scss reset (jakby co użyłam tam box-sizing: border-box)
- base/\_typography.scss - selekcja używanych fontów
- base/\_page.scss - ogólne style do wszystkich stron
- base/\_colours.scss - wyróżnione kolory wiodące
- layout/\_menu.scss - style do nagłówka strony i menu
- pages/\_index.scss - style używane w index.html
- menu.js - taki mini js - potrzebny do menu
- index.html - strona główna
- assets - grafika

# Porady

- czasami preprocesor nie ogarnia zmian (szczególnie przy imporcie zmiennych) - polecam zatrzymać śledzenie zmian i włączenie na nowo w takich przypadkach (czasami kilka razy)

# Instrukcja dodawania nowej stronki html

## Jak zmieniasz coś w szkielecie menu, to zmień na wszystkich stronach (np nazwa linka do podstrony)

- skopiuj index.html
- wywal wszystko z tagu main
- usuń ostatni lik do stylów (do index.scss)
- kontent dodawaj do tagu main - nie poza niego, bo się menu może rozjechać !
- style specyficzne dla strony pisz w odpowiadającym pliku stylów

- jakzmieniacie coś w menu, to trzeba poprawić w każdym html

# Pomysły

- można dodać w menu na ząbku link do czegoś/innej strony
- chilowo dodałam w menu zakładki: O nas i Kontakt, ale jak macie inne pomyły, to inne strony możecie stowrzyć

# Po zakończeniu implementacji listy

- usunąć wszystkie niepotrzebne foldery/pliki z css i scss
- część folderów ma w sobie randomowy plik - tylk dlatego, że git mi nie doda folderu bez zawartości do repo - po implementacji można wywalić
