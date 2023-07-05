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

# Drop tables in the database
def drop_tables(conn):
    try:
        cursor = conn.cursor()

        # Drop 'tasks' table
        cursor.execute("DROP TABLE IF EXISTS tasks")
        print("Table 'tasks' dropped successfully.")

        # Drop 'epics' table
        cursor.execute("DROP TABLE IF EXISTS epics")
        print("Table 'epics' dropped successfully.")

        # Drop 'users' table
        cursor.execute("DROP TABLE IF EXISTS users")
        print("Table 'users' dropped successfully.")

        conn.commit()
        cursor.close()
        return True
    except psycopg2.Error as e:
        print("Error dropping tables:", e)
        return False

# Main function
def main():
    conn = connect_to_postgres()
    if conn:
        conn.set_session(autocommit=False)
        if drop_tables(conn):
            print("All tables dropped successfully.")
        else:
            print("Failed to drop tables.")
        conn.close()
    else:
        print("Failed to connect to the database.")

if __name__ == "__main__":
    main()
