# SIFROVANJE: Ako je poruka npr. od 10 karaktera, vidimo sa kojim je brojem deljiva ta poruka 
# i da li ima ostatka da bi smo znali koje su dimenzije kolona u jednom redu. 
# Broj redova matrice je kljuc! 
# Kada smo odredili matricu,   ***POPUNJAVAMO PO KOLONAMA***   porukom, nakon toga   ***ISCITAVAMO PO REDOVIMA***   i 
# rezultate iscitavanja nadodajemo u sifrovani string i taj string je sifrat.
# Treba uvesti i ogranicenja da broj redova ne sme biti 1 ili manji 
# (jer bukvalno sifrovana i desifrovana poruka bi bile iste, i onda ne mozemo iscitati iz drugih redova).
# Treba dodati jos da broj redova ne sme biti veci od duzine poruke, jer ako bi se uneo preveliki kljuc 
# ne bi imalo smisla da matrica ima vise redova nego nego karaktera u poruci.

# DESIFROVANJE: pravimo matricu istih dimenzija, posto ovde popunjavamo sifratom, popunjava se suprotno od sifrovanja tj.   ***POPUNJAVAMO PO REDOVIMA***  , da bi smo dobili matricu iste sadrzine kao u sifrovanju.
# Iz matrice citamo suprotno generisanju sifrata, tj.   ***ISCITAVAMO PO KOLONAMA***  .

# Razlika izmedju sifrovanja i desifrovanja je u: 
# 1. popunjavanju matrice
# 2. citanju iz matrice
# tj. u desifrovanju moraju ove dve radnje da se urade suprotno nego sto je odradjeno u sifrovanju


class ScytaleCipher:
    
    @staticmethod
    def encrypt(poruka, broj_redova):                                               #broj redova je u stvari kljuc
        sifrovana_poruka = ""

        if broj_redova >= len(poruka) or broj_redova <= 1:
            return "Kljuc ne sme biti veci od velicine poruke i mora biti veci od 1."
        else:
            while (len(poruka) % broj_redova != 0):                                 #ovo radimo da bi duzina poruke bila deljiva sa brojem redova da bi smo dobili broj kolona koji je ceo broj, da smo primenili samo celobrojno deljenje // pitanje je na koji broj bi zaokruzilo, ovako smo sigurni
                poruka += " "                                                       #dodamo prazna polja tako da bi poruka bila jednaka za brojem redova, tj. da ne bi bilo viska
            
            broj_kolona = len(poruka) / broj_redova
            broj_kolona = int(broj_kolona)

            #niz_nizova = broj_redova*[broj_kolona*["Z"]]                           #matrica ne moze da se generise ovako i necu moci da koristim npr:   niz_nizova[i][j] = l[x]
            l = []
            for char in poruka:                                                     #unesenu poruku pretvaramo u niz da bi smo mogliu da iteriramo kroz poruku da bi manipulisali svakim pojedinacnim karakterom
                l.append(char)

            x = 0                                                                   #brojac za niz karaktera od poruke
            niz = []                                                        
            niz_konacni = []                                                        #ovde ne mogu da radim ovako:  niz[i][j] = l[x],  jer nisam napravio matricu, prvo moram da je napravim kroz for petlju
            for i in range(broj_redova):                                            #ali sam isti efekat dobio kao i sa   niz[i][j] = l[x]  jer sam u nju odma ubacio vrednosti                           
                for j in range(broj_kolona):                                
                    niz.append(l[x])                
                    x += 1                                                          #niz je svaki pojedinacan red, a niz_konacni je matrica u koju ubacujemo redove

                niz_konacni.append(niz)                                             #ovo radim da bih uopste napravio matricu i da bih popunio vrednostima
                niz = []                                                            #ispraznim niz da bi mogao ponovo da ga koristim

            print(niz_konacni)                                                      #print matrice pre iscitavanja iz nje
            
            for z in range(broj_kolona):                                            #karaktere iz matrice citam po redovima, npr. element na indeksu [0] iz svih redova, pa element na indeksu [1] iz svih redova itd.
                for x in range(broj_redova):
                    sifrovana_poruka += niz_konacni[x][z]

            return sifrovana_poruka


    @staticmethod
    def decrypt(poruka, broj_redova):
        desifrovana_poruka = ""

        if broj_redova >= len(poruka) or broj_redova <= 1:
            return "Kljuc ne sme biti veci od velicine poruke i mora biti veci od 1."
        else:
            while (len(poruka) % broj_redova != 0):
                poruka += " "                           
            
            broj_kolona = len(poruka) / broj_redova
            broj_kolona = int(broj_kolona)


            #poenta je da popunjene matrice (iz funkcije encrypt i decrypt) budu iste u dimenzijama i sadrzaju! i zbog toga se radi sledece:
            #posto ovde imamo sifrat a ne izvnornu poruku moramo popniti po redovima, 
            #a za to nam treba prvo prazna matrica zejenih dimenzija, da bih u popunjavanju sadrzajem mogao da pristupam sa 'niz_konacni[br_kolona][br_redova] = karakter_iz_sifrata'
            niz = []                                    
            niz_konacni = []                                         
            
            for i in range(broj_redova):                                    #ovde se samo pravi prazna matrica, jer moram da je popunim po redovima (a to naravno ne mogu u jednom koraku da uradim kao u encrypt, jer ne mogu da pristupam poziciji i da stavljam element na nju kad jos uvek ne postoji) 
                for j in range(broj_kolona):            
                    niz.append("")                    

                niz_konacni.append(niz)                 
                niz = []                                


            l = []
            for char in poruka:                         
                l.append(char)

            x = 0                                                           #matrica se mora popuniti po redovima, jer je sifrat u pitanju, ne izvorna poruka, na taj nacin dobijamo istu matricu kao u encrypt i onda je samo iscitamo suprotnim redosledom
            for i in range(broj_kolona):         
                for j in range(broj_redova):
                    niz_konacni[j][i] = l[x]
                    x+=1

            print(niz_konacni)                                              #print matrice pre iscitavanja iz nje

            #karaktere iz matrice citam po kolonama. NARAVNO USLOV JE DA SAM PRETHODNO POPUNIO MATRICU SUPROTNO OD POPUNJAVANJA U FUNKCIJI ENCRYPT
            for z in range(broj_redova):         
                for x in range(broj_kolona):
                    desifrovana_poruka += niz_konacni[z][x]

            return desifrovana_poruka

