package com.example.jetspacego.model.rocket

data class RocketModel(
    val id: Int,
    val name: String,
    val description: String,
    val url: String
)

val rocketList = listOf(
    RocketModel(
        id = 1,
        name = "PSLV (Polar Satellite Launch Vehicle)",
        description = "ISRO's most reliable launcher • 4-stage design (solid-liquid-solid-liquid) • Used for polar/Sun-synchronous orbits • Carries up to 1,750 kg to SSO",
        url = "https://raw.githubusercontent.com/SmitKarwade/Rocket3d/main/rocket_anim.glb"
    ),
    RocketModel(
        id = 2,
        name = "GSLV (Geosynchronous Satellite Launch Vehicle)",
        description = "India’s heavy-lifter • Cryogenic upper stage • Used for communication satellites • Up to 2,500 kg to GTO • Three-stage launch system",
        url = "https://raw.githubusercontent.com/SmitKarwade/Rocket3d/main/gslv.glb"
    ),
    RocketModel(
        id = 3,
        name = "Falcon 9",
        description = "SpaceX’s reusable rocket • Two-stage, Merlin engines • Launches Starlink, ISS cargo • Lands booster on droneships • Payload: up to 22,800 kg to LEO",
        url = "https://raw.githubusercontent.com/SmitKarwade/Rocket3d/main/falcon_9_-_spacex.glb"
    ),
    RocketModel(
        id = 4,
        name = "Mercury Atlas",
        description = "Used by NASA for Mercury missions • First to orbit an American • Based on Atlas-D missile • 1-stage with boosters • John Glenn's launch vehicle",
        url = "https://raw.githubusercontent.com/SmitKarwade/Rocket3d/main/mercury_atlas.glb"
    ),
    RocketModel(
        id = 5,
        name = "Mercury Redstone",
        description = "Short suborbital flights • Alan Shepard's historic launch • Based on Redstone missile • Single-stage liquid-fueled • Max altitude ~190 km",
        url = "https://raw.githubusercontent.com/SmitKarwade/Rocket3d/main/mercury_redstone.glb"
    ),
    RocketModel(
        id = 6,
        name = "SLS (Space Launch System)",
        description = "NASA’s super heavy-lift rocket • Supports Artemis Moon missions • Solid rocket boosters + RS-25 core • Payload: up to 95,000 kg to LEO",
        url = "https://raw.githubusercontent.com/SmitKarwade/Rocket3d/main/sls.glb"
    )
)