Do uruchomienia programu potrzebny jest Python w wersji 3 (program
testowano na wersji 3.12.0, nie ma gwarancji wstecznej kompatybilności)
wraz z bibliotekami regex oraz graphviz. Jeśli biblioteki nie są
zainstalowane, należy je zainstalować ręcznie lub z katalogu main wywołać
następującą komendę:

pip install -r requirements.txt

Dane wejściowe znajdują się w plikach .txt w katalogu main/inputs. Pliki te
wymagają odpowiedniego formatu. Przykładowy plik znajduje się w katalogu inputs.

Aby uruchomić program, z poziomu katalogu main należy wywołać
następującą komendę:

python main.py

Wejściem jest plik input.txt znajdujący się w katalogu inputs.
Wyjście jest wypisywane w konsoli oraz zapisywane do pliku result.txt w
katalogu output.