package com.xyz.marcelo.repository

import com.xyz.marcelo.data.model.User
import com.xyz.marcelo.data.table.UserTable
import com.xyz.marcelo.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class repo {

    suspend fun addUser(user: User) {
        dbQuery {
            UserTable.insert { ut ->
                ut[UserTable.email] = user.email
                ut[UserTable.hashPassword] = user.hashPassword
                ut[UserTable.name] = user.userName
            }
        }
    }

    suspend fun findUserByEmail(email: String) = dbQuery {
        UserTable.select { UserTable.email.eq(email) }
            .map { rowToUser(it) }
            .singleOrNull()
    }

    private fun rowToUser(row: ResultRow?): User? {
        if (row == null) {
            return null
        }

        return User(
            email = row[UserTable.email],
            hashPassword = row[UserTable.hashPassword],
            userName = row[UserTable.name],
        )
    }
}
