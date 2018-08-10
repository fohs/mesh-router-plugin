# Mesh Router Plugin

Simple plugin to enable configurable routes in Gentics Mesh CMS

## Getting Started

1. `git clone https://github.com/fohs/mesh-router-plugin`

2. `cd mesh-router-plugin`

3. `./gradlew` or `gradlew.bat`

4. Copy `build/libs/mesh-router-plugin-1.0-SNAPSHOT.jar` into `plugins` directory in Mesh home directory (create one if doesn't exist).

5. Create `routing.json` in `config` directory in Mesh home directory and provide routes definition, like below:
    
    ```
    {
      "routes": [
        {
          "method": "GET",
          "serverPath": "/test",
          "systemPath": "test"
        },
        {
          "method": "GET",
          "serverPath": "/app1*",
          "systemPath": "my-spa-app/dist"
        }
      ]
    }
    ```
6. Start up Mesh and enjoy your new routes

