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

def add_columns(connection):

    try:
        cursor = connection.cursor()
        # Verifica se a coluna já existe antes de criar para evitar erros
        cursor.execute("SELECT column_name FROM information_schema.columns WHERE table_name='tasks' AND column_name='user_id'")
        if not cursor.fetchone():
            cursor.execute("ALTER TABLE tasks ADD COLUMN user_id VARCHAR(255) NOT NULL")
            connection.commit()
            print("Coluna 'user_id' adicionada com sucesso.")
        else:
            print("A coluna 'user_id' já existe na tabela 'tasks'.")

        # Verifica se a coluna já existe antes de criar para evitar erros
        cursor.execute("SELECT column_name FROM information_schema.columns WHERE table_name='epics' AND column_name='user_id'")
        if not cursor.fetchone():
            cursor.execute("ALTER TABLE epics ADD COLUMN user_id VARCHAR(255) NOT NULL")
            connection.commit()
            print("Coluna 'user_id' adicionada com sucesso.")
        else:
            print("A coluna 'user_id' já existe na tabela 'epics'.")

        # Verifica se a coluna já existe antes de criar para evitar erros
        cursor.execute("SELECT column_name FROM information_schema.columns WHERE table_name='epics' AND column_name='total_tasks'")
        if not cursor.fetchone():
            cursor.execute("ALTER TABLE epics ADD COLUMN total_tasks INT")
            connection.commit()
            print("Coluna 'total_tasks' adicionada com sucesso.")
        else:
            print("A coluna 'total_tasks' já existe na tabela 'epics'.")

        # Verifica se a coluna já existe antes de criar para evitar erros
        cursor.execute("SELECT column_name FROM information_schema.columns WHERE table_name='epics' AND column_name='tasks_done'")
        if not cursor.fetchone():
            cursor.execute("ALTER TABLE epics ADD COLUMN tasks_done INT")
            connection.commit()
            print("Coluna 'tasks_done' adicionada com sucesso.")
        else:
            print("A coluna 'tasks_done' já existe na tabela 'epics'.")

    except (Exception, psycopg2.Error) as error:
        connection.rollback()
        print("Erro ao adicionar a coluna 'user_id':", error)
    
    finally:
        if connection:
            cursor.close()
            connection.close()
    

if __name__ == "__main__":
    try:
        # Conecta-se ao banco de dados
        connection = connect_to_database()

        # Adiciona a coluna "in_progress"
        add_columns(connection)

    except (Exception, psycopg2.Error) as error:
        print("Erro inesperado:", error)