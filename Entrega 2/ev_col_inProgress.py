import psycopg2

def connect_to_database():
    try:
        connection = psycopg2.connect(
            host="localhost",
            port="5432",
            user="postgres",
            password="password",
            database="todo"
        )
        return connection
    except (Exception, psycopg2.Error) as error:
        print("Erro ao conectar ao banco de dados:", error)

def add_column_in_progress(connection):
    try:
        cursor = connection.cursor()
        # Verifica se a coluna já existe antes de criar para evitar erros
        cursor.execute("SELECT column_name FROM information_schema.columns WHERE table_name='tasks' AND column_name='in_progress'")
        if not cursor.fetchone():
            cursor.execute("ALTER TABLE tasks ADD COLUMN in_progress BOOLEAN DEFAULT TRUE")
            connection.commit()
            print("Coluna 'in_progress' adicionada com sucesso.")
        else:
            print("A coluna 'in_progress' já existe na tabela 'tasks'.")
    except (Exception, psycopg2.Error) as error:
        connection.rollback()
        print("Erro ao adicionar a coluna 'in_progress':", error)
    finally:
        if connection:
            cursor.close()
            connection.close()

if __name__ == "__main__":
    try:
        # Conecta-se ao banco de dados
        connection = connect_to_database()

        # Adiciona a coluna "in_progress"
        add_column_in_progress(connection)

    except (Exception, psycopg2.Error) as error:
        print("Erro inesperado:", error)