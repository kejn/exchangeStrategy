# exchangeStrategy
# PL: GIELDA PAPIEROW WARTOSCIOWYCH
Napisac program sprawdzajacy rozne strategie obrotu akcjami. Dane wejsciowe beda dostarczone w pliku CSV.                
# Zasady
1.	Sprzedaz i kupowanie akcji odbywa sie w biurze maklerskim, pobierajacym prowizje w wysokosci 0.5% od kazdej transakcji
2.	Kursy akcji raz dziennie aktualizuje Gielda
3.	Po zmianie kursów Gracz moze kupic i sprzedac dowolna ilosc akcji, jezeli posiada odpowiednia ilosc pieniedzy
4.	Gracz zaczyna z pustym portfelem akcji i 10000 PLN w portfelu z pieniedzmi
5.	Aplikacja ma umozliwiac przetestowanie róznych strategii kupna/sprzedazy
6.	Dostepnosci akcji i chetnych na kupno akcji nie bierzemy pod uwage    

Dane zostana dostarczone w formacie tekstowym, np:                                                                        
INTEL,20011024,105.3                                                                                                    
MICROSOFT,20011024,15.1                                                                                                 
INTEL,20011025,105.4                                                                                                    
MICROSOFT,20011025,16.2                                                                                                 
MICROSOFT,20011026,15.0                                                                                                  
INTEL,20011026,101.3                                                                                                    

Aplikacja powinna byc niezalezna od ilosci i nazw firm na gieldzie a tazke od okresu czasu zawartego w pliku. 
Kolejnosc wpisow pliku zrodlowym moze byc przypadkowa.
