import { ExamplePost } from "@/src/types/exampleTypes";
import { useGetPostsQuery } from "@/src/queries/examplePostApi";

export default function ExamplePostList() {
  const { data: posts = [], isFetching } = useGetPostsQuery();

  if (isFetching) {
    return <div>Loading...</div>;
  }

  return (
    <ul>
      <li>이야아</li>
    </ul>
  );
}
