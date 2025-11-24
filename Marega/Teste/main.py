from fastapi import FastAPI

app = FastAPI()


@app.get("/products")
async def get_products():
    return {
        "status": "ok",
        "products": [
            {
                "id": 1,
                "name": "Apple iPhone 15 Pro",
                "price": 1199.99,
                "description": "O mais recente iPhone com chip A17 Pro, câmara tripla e design em titânio.",
                "image_url": "https://cdsassets.apple.com/live/7WUAS350/images/tech-specs/iphone_15_pro.png"
            },
            {
                "id": 2,
                "name": "Samsung Galaxy S24 Ultra",
                "price": 1099.99,
                "description": "Smartphone topo de gama da Samsung com S Pen, ecrã AMOLED e câmara de 200MP.",
                "image_url": "https://www.coolmatica.pt/store/322080-large_default/smartphone-samsung-galaxy-s24-ultra-173cm-68-dual-sim-5g-usb-type-c-12gb-256gb-5000mah-cinzento-titanio.jpg"
            },
            {
                "id": 3,
                "name": "Sony WH-1000XM5",
                "price": 399.99,
                "description": "Auscultadores com cancelamento de ruído líder na indústria e som premium.",
                "image_url": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQTQ3aBpq04l052rWuPphU1BKPNBvW5JWJVA&s"
            },
            {
                "id": 4,
                "name": "MacBook Air M3",
                "price": 1299.99,
                "description": "Portátil leve e poderoso com o novo chip M3 e até 18h de bateria.",
                "image_url": "https://thumb.pccomponentes.com/w-530-530/articles/1081/10819860/1484-apple-macbook-air-apple-m3-8gb-256gb-ssd-gpu-8-nucleos-136-medianoche-1295403c-57e9-4ebd-9379-d8e610d82fa0.jpg"
            },
            {
                "id": 5,
                "name": "PlayStation 5",
                "price": 549.99,
                "description": "Consola de nova geração com gráficos 4K, SSD ultrarrápido e novos DualSense controllers.",
                "image_url": "https://m.media-amazon.com/images/I/51NbBH89m1L.jpg"
            }
        ]
    }