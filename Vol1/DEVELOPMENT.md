# SpringBootTestWorkshop

Spring Boot でのテスト記述を学ぶための小さなワークショップ用リポジトリです。  
シンプルなユーザー年齢計算 API を題材に、ユニットテストから Web 層／DB 層のスライス テストまでを段階的に体験できます。

## プロジェクト概要
- `GET /users/{userId}/age` で指定ユーザーの年齢を返却します。
- H2 (インメモリ) + MyBatis で永続化レイヤーを構成し、`schema.sql` でテーブルを初期化します。
- `DateTimeUtil` を介して `LocalDate.now()` をラップし、テストから日付を差し替えられるようにしています。

```
src/main/java
├─ controller/UserController.java     # Web API
├─ service/UserService.java           # ビジネスロジック
├─ repository/UserMapper.java         # MyBatis Mapper
├─ util/{DateTimeUtil, UserUtil}.java # 補助ロジック
└─ bean/User.java                     # レコード
```

## 学べるテストの種類
| 目的 | 対象ファイル | ポイント |
| --- | --- | --- |
| 純粋関数のユニットテスト | `src/test/java/.../util/UserUtilTest.java` | JUnit で例外／値の両方を検証 |
| モックを使ったサービス層テスト | `.../service/UserServiceTest.java` | Mockito で依存 (`DateTimeUtil`, `UserMapper`) を差し替え |
| DB アクセスのスライステスト | `.../repository/UserMapperTest.java` | `@MybatisTest` + `@Sql` で H2 にセットアップしたデータを検証 |
| Web 層 (Controller) テスト | `.../controller/UserControllerTest.java` | `@WebMvcTest` + `MockMvc` で HTTP レイヤーをテスト |

## 事前準備
- JDK 21
- Maven 3.9 以降 (または同梱の `mvnw` / `mvnw.cmd`)

## テストの実行
全テストを実行する場合:

```bash
./mvnw clean verify
```

特定のテストだけを流したい場合は `-Dtest=UserServiceTest` のように指定できます。  
テストコード内のコメントも参照しながら、モックの扱いや `@Sql` を使ったデータ投入方法を確認してください。

## 次のステップ例
- `TODO` になっているパターン (例: 誕生日の前日／翌日) のテストケースを追加する。
- Controller テストに異常系 (存在しないユーザー ID) を追加し、`UserService` の例外の扱いを検討する。
- Testcontainers や Flyway を使い、より実践に近いテスト環境を構築する。


