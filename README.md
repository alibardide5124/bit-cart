# Bit-Cart - PC Part E-Commerce application

```
Important note
> This application requires a supabase server, which at this moment is indefinitly
 shutdown, as it was a final project for my university and I didn't intended to keep
 it running. Therefore no release build is available, and you have to build your
 own supabase server to run it.
```
Bit-Cart is a shopping application with minimal features designed based on supabase server.

### Features
It features base E-Commerce functionality, including:
- Browse catalog
- Mange shopping cart
- Login via Google OR email /w password
- Place and track orders

### Arcitecture diagram
![Diagram]("./docs/bit-cart-diagram.png")

### Repo structure
```
.
├── app/src/main/
│   ├── java/com/alibardide/bit_cart/
│   │   ├── data/
│   │   ├── screen/
│   │   ├── ui/
│   │   ├── utils/
│   │   ├── MainActivity.kt
│   │   ├── ...
│   ├── res/
│   │   ├── drawable/
│   │   ├── mipmap/
│   │   ├── ...
│   ├── AndroidManifest.xml/
├── gradle/
│   └── ...
├── LISENCE
└── README.md
```

### Tech stack
- **Kotlin** for programming android application
- **Jetpack compose** to design declaritive android ui
- **supabase** as a server
- **postgresql** for database
- **google credential manager** to manage user google accounts and request login

### Support
You can give project a start and join [**stargazers**](https://github.com/alibardide5124/bit-cart/stargazers) for this repository
<br/>
And [**follow me**](https://github.com/alibardide5124?tab=followers) for my next creations

### License
Bit-Cart by [Ali Bardide](https://github.com/alibardide5124) is licensed under a [MIT License](https://mit-license.org/).
