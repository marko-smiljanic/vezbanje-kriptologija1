
broj = 0
red = 0
kolona = 0
poslednji_brojevi_koliko_ih_je = 0

i = 0
j = 0
while i <= red:
    while j <= red:
        broj += 1
        print(broj, "", end='')
        j += 1

    poslednji_brojevi_koliko_ih_je = j
    if( broj >= 91):
        break
    print("\n")
    j = 0
    red += 1
    i += 1

print("\n")
print("ukupno brojeva je: ", poslednji_brojevi_koliko_ih_je)

##################
broj = 0
red = 0
kolona = 0
#poslednji_brojevi_koliko_ih_je = 0

i = 0
j = 0

pocetnaKordinata = poslednji_brojevi_koliko_ih_je
print("pocetna kordinata je: ", pocetnaKordinata)
while i <= red:
    if broj == 10:
        pocetnaKordinata -= 1                #ovo je cisto da jelka bude pravilnija, jer su posle u redu sve dvocifreni brojevi i onda ne mogu da budu uravnati sa jednocifrenim lepo
    for z in range(pocetnaKordinata):
        print(" ", end='')

    while j <= red:
        broj += 1
        print(broj, "", end='')
        j += 1

    if( broj >= 91):
        break
    print("\n")
    j = 0
    pocetnaKordinata -= 1
    i += 1
    red += 1




##########################################
print("\n")

    
broj = 0

i = 0
j = 0
for i in range(10):
    while j <= i:
        broj += 1
        print(broj, "", end='')
        j += 1

    if( broj >= 91):
        break
    print("\n")
    j = 0
    i += 1
    