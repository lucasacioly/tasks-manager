import psycopg2

# Connect to PostgreSQL
def connect_to_postgres():
    try:
        conn = psycopg2.connect(
            host="localhost",
            port="5432",
            user="postgres",
            password="password",
            database="todo"
        )
        return conn
    except psycopg2.Error as e:
        print("Error connecting to PostgreSQL:", e)
        return None

# Create tables in the database
def create_tables(conn):
    try:
        cursor = conn.cursor()

        # Create 'epics' table
        cursor.execute("""
            CREATE TABLE IF NOT EXISTS epics (
                id SERIAL PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                description TEXT NOT NULL
            )
        """)
        print("Table 'epics' created successfully.")

        # Create 'tasks' table
        cursor.execute("""
            CREATE TABLE IF NOT EXISTS tasks (
                id SERIAL PRIMARY KEY,
                epic_id INT,
                name VARCHAR(100) NOT NULL,
                description TEXT NOT NULL,
                due_date DATE,
                FOREIGN KEY (epic_id) REFERENCES epics(id)
            )
        """)
        print("Table 'tasks' created successfully.")

        # Create 'users' table
        cursor.execute("""
            CREATE TABLE IF NOT EXISTS users (
                id SERIAL PRIMARY KEY,
                username VARCHAR(100) NOT NULL,
                email VARCHAR(100) NOT NULL,
                password VARCHAR(100) NOT NULL,
                oauth_token VARCHAR(200)
            )
        """)
        print("Table 'users' created successfully.")

        conn.commit()
        cursor.close()
        conn.close()
        return True
    except psycopg2.Error as e:
        print("Error creating tables:", e)
        return False

# Main function
def main():
    conn = connect_to_postgres()
    if conn:
        conn.set_session(autocommit=False)
        if create_tables(conn):
            print("All tables created successfully.")
        else:
            print("Failed to create tables.")
    else:
        print("Failed to connect to the database.")

if __name__ == "__main__":
    main()
