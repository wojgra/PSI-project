import React, { useEffect, useState } from 'react';

const Checkout = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        // Pamiętaj: Używanie klucza tajnego w kodzie klienta jest niebezpieczne.
        // W produkcji stwórz endpoint backendowy, który bezpiecznie komunikuje się z Stripe.
        const response = await fetch('https://api.stripe.com/v1/products', {
          headers: {
            'Authorization': `Bearer ${process.env.STRIPE_SECRET_KEY}`,
            'Content-Type': 'application/json',
          },
        });
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        setProducts(data.data || []); // Stripe zwraca produkty w polu "data"
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, []);

  return (
    <div className="sr-root">
      <div className="sr-main">
        <section className="container">
          <h1>Dostępne produkty</h1>
          {loading ? (
            <p>Ładowanie produktów...</p>
          ) : error ? (
            <p>Error: {error}</p>
          ) : products.length > 0 ? (
            <ul>
              {products.map((product) => (
                <li key={product.id}>
                  <h2>{product.name}</h2>
                  {product.description && <p>{product.description}</p>}
                  {product.images && product.images.length > 0 && (
                    <img
                      src={product.images[0]}
                      alt={product.name}
                      width="140"
                      height="160"
                    />
                  )}
                </li>
              ))}
            </ul>
          ) : (
            <p>Brak produktów do wyświetlenia.</p>
          )}
          <form action="/api/create-checkout-session" method="POST">
            <button id="submit" role="link">Buy</button>
          </form>
        </section>
      </div>
    </div>
  );
};

export default Checkout;