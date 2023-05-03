// Auth State
export interface ExampleState {
  exampleUser: ExampleUser | null;
  exampleToken: string | null;
  exampleProducts: any[] | [];
}

// User
export interface ExampleUser {
  exampleId: number;
  exampleEmail: string;
  exampleName: string;
}

// Auth Action Payload
export interface ExampleAuthPayload {
  exampleUser?: ExampleUser;
  exampleToken?: string;
}

// Post (게시글)
export interface ExamplePost {
  exampleId: number;
  exampleTitle: string;
  exampleBody: string;
}
